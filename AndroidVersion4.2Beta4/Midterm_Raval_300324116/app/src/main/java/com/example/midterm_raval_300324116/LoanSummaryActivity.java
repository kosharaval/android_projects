package com.example.midterm_raval_300324116;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoanSummaryActivity extends AppCompatActivity {

    private float floatPayment;
    private float floatPrice;

    TextView textViewMonthlyPayment;
    TextView textViewSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_summary);

        textViewMonthlyPayment = findViewById(R.id.textViewMonthlyPayment);
        textViewSummary = findViewById(R.id.textViewSummary);
        Button btnRetrun = findViewById(R.id.buttonRetrun);

        btnRetrun.setOnClickListener((View view)->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //floatPrice = savedInstanceState.getFloat("price");
        //floatPayment = savedInstanceState.getFloat("payment");
        String stringPrice = savedInstanceState.getString("price");
        textViewMonthlyPayment.setText( stringPrice);
    }
}