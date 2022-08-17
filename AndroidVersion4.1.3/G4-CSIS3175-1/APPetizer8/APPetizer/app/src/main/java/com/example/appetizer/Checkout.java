package com.example.appetizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Checkout extends AppCompatActivity {
    //float subtotal= 0;
    double subtotal = 0;
    double _tax = 0;
    double _discount = 0;
    double _delivery = 0;
    double _total = 0;
    int orderId = 0;
    int userId = 0;

    DecimalFormat df = new DecimalFormat("$###.##");
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

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

        boolean discount = dbh.isAvailable(userId);

        //SUBTOTAL
        TextView tvCkoutSubTotal = findViewById(R.id.tvCkoutSubTotal);
        tvCkoutSubTotal.setText("SUBTOTAL " + df.format(subtotal));

        //TAX
        TextView tax = (findViewById(R.id.tvCkoutTax));
        _tax = subtotal * 0.06;

        tax.setText("Tax: " + df.format(_tax));

        if (discount){
            subtotal -= (subtotal*10 / 100);
            TextView tvDiscount = findViewById(R.id.tvDiscount);
            tvDiscount.setText("10% discount applied.");
            dbh.setUsedDiscount(userId);
        }

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
    }

    public void ConfirmOrder(View view){
        //pegar os valores da tela
        Switch delivery = findViewById(R.id.delivery);
        boolean isDelivery = delivery.isChecked();
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

        //RadioButton rbCC = (RadioButton) findViewById(R.id.rgPayment);
        RadioGroup rgPayment = findViewById(R.id.rgPayment);

        switch (rgPayment.getCheckedRadioButtonId()){
            case R.id.rbCC:
                order.setPayment("Credit Card");
                break;
            case R.id.rbDC:
                order.setPayment("Debit Card");
                break;
            case R.id.rbMoney:
                order.setPayment("Money");
                break;
        }

        //gravar
        DatabaseHelper dbh = new DatabaseHelper(this);
        boolean confirmed = dbh.updateOrder(order);

        if(confirmed){
            //apagar a ordem da shared pref
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("orderId");
            editor.commit();
            //mandar para a Restaurant list
            Intent gotoRestaurantList = new Intent(Checkout.this, RestaurantList.class);
            Toast.makeText(this, "Order confirmed", Toast.LENGTH_LONG).show();
            this.finish();
            startActivity(gotoRestaurantList);
        }else{
            Toast.makeText(this, "Please check your order details", Toast.LENGTH_LONG).show();
        }

    }
}
