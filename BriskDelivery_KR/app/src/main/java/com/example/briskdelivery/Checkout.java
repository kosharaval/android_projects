package com.example.briskdelivery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import Config.Config;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Checkout extends AppCompatActivity {
    double subtotal = 0;
    double _tax = 0;
    double _delivery = 0;
    int orderId = 0;
    int userId = 0;
    private static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(Config.PAYPAL_CLIENT_ID);
    DecimalFormat df = new DecimalFormat("$###.##");
    TextView tvTotal;

    @Override
    protected void onDestroy(){
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent =  new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        Button btnCkoutConfirm = findViewById(R.id.btnCkoutConfirm);

        //paypal
        DatabaseHelper dbh = new DatabaseHelper(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        orderId = sp.getInt("orderId", 0);
        userId = sp.getInt("userId", 0);

        Cursor c = dbh.GetSubTotal(orderId);

        if(c.getCount() > 0){
            while (c.moveToNext()){
                subtotal += c.getFloat(0);
            }
        }

        //SUBTOTAL
        TextView tvCkoutSubTotal = findViewById(R.id.tvCkoutSubTotal);
        tvCkoutSubTotal.setText("SUBTOTAL " + df.format(subtotal));

        //TAX
        TextView tax = (findViewById(R.id.tvCkoutTax));
        _tax = subtotal * 0.06;

        tax.setText("Tax: " + df.format(_tax));


        tvTotal = findViewById(R.id.tvOrderTotal);
        tvTotal.setText("Total: " + df.format(subtotal + _tax) );

        final Switch delivery = findViewById(R.id.delivery);
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvFee = findViewById(R.id.tvFee);

                if(delivery.isChecked()){
                    tvFee.setText("Delivery Fee: $2.00");
                    _delivery = 2.00;
                }else{
                    tvFee.setText("");
                    _delivery = 0.00;
                }
                tvTotal.setText("Total: " + df.format(subtotal + _delivery + _tax) );

            }
        });

        btnCkoutConfirm.setOnClickListener((View v) -> {
            Switch deliverySwitch = findViewById(R.id.delivery);
            boolean isDelivery = deliverySwitch.isChecked();
            Orders order = new Orders();
            order.setOrderId(orderId);
            order.setUserId(userId);
            Date date = new Date();
            order.setDate(DateFormat.getDateInstance().format(date));
            order.setStatus(true);
            order.setType(isDelivery);
            order.setTotal(subtotal);
            order.setDelivery(isDelivery ? 2.00 : 0.00);
            order.setTax(_tax);
            boolean confirmed = dbh.updateOrder(order);
            if(confirmed){
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("orderId");
                editor.commit();
                PayPalPayment pp = new PayPalPayment(new BigDecimal(subtotal),"USD","Donate",PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent1 = new Intent(this, PaymentActivity.class);
                intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                intent1.putExtra(PaymentActivity.EXTRA_PAYMENT,pp);
                startActivityForResult(intent1,PAYPAL_REQUEST_CODE);
            }else{
                Toast.makeText(this, "Please check your order details", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                PaymentConfirmation pc = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(pc != null){
                    try{
                        String pDtls = pc.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class)
                        .putExtra("PaymentDetails",pDtls)
                         .putExtra("paymentAmount", subtotal)
                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT);
        }
        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT);
        }
    }


}
