package com.example.briskdelivery;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {


    TextView paypalId, paypalAmnt, paypalSt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        Button bckbutton = findViewById(R.id.bckbutton);
        paypalId = findViewById(R.id.paypalId);
        paypalAmnt = findViewById(R.id.paypalAmnt);
        paypalSt = findViewById(R.id.paypalSt);
        Intent intent = getIntent();
        try {
            JSONObject jj = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jj.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        bckbutton.setOnClickListener((View v) -> {

            startActivity(new Intent(PaymentDetails.this, Buttons.class)
            );
        });


    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try{
            paypalId.setText(response.getString("id"));
            paypalAmnt.setText(response.getString("state"));
            paypalSt.setText(response.getString(String.format(("$%s"),paymentAmount)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}