package com.example.midterm_raval_300324116;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private float floatPayment;
    private float floatPrice;

    EditText editTextPrice;
    EditText editTextPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPrice = findViewById(R.id.editTextPrice);
        editTextPayment = findViewById(R.id.editTextPayment);

       //floatPrice = Float.parseFloat(editTextPrice.toString());
        //floatPayment = Float.parseFloat(editTextPayment.toString());

        Button btnLoanReport = findViewById(R.id.buttonLoanReport);
        btnLoanReport.setOnClickListener((View view)->{
            Intent intentReport = new Intent(this,LoanSummaryActivity.class);
            startActivity(intentReport);
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("price",editTextPrice.toString());
        outState.putString("payment",editTextPayment.toString());

    }

}