package com.example.lab04_300324116;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    double noOfPrints;
    double priceSmall = 19;
    double priceMedium = 49;
    double priceLarge = 79;
    double totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText numberOfPrints = (EditText) findViewById(R.id.txtNumber);
        final RadioButton small = (RadioButton) findViewById(R.id.radioSmall);
        final RadioButton medium = (RadioButton) findViewById(R.id.radioMedium);
        final RadioButton large = (RadioButton) findViewById(R.id.radioLarge);
        final TextView result =(TextView) findViewById(R.id.txtResult);

        Button print = (Button) findViewById(R.id.btnPrint);

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                noOfPrints = Double.parseDouble(numberOfPrints.getText().toString());
                DecimalFormat currency = new DecimalFormat("$###,###.##");

                if(noOfPrints <= 50)
                {
                    if(small.isChecked())
                    {
                        totalCost = (noOfPrints * priceSmall) / 100;
                        result.setText("The order cost is " + currency.format(totalCost));
                    }
                    else if(medium.isChecked())
                    {
                        totalCost = (noOfPrints * priceMedium) / 100;
                        result.setText("The order cost is " + currency.format(totalCost));
                    }
                    else if(large.isChecked())
                    {
                        totalCost = (noOfPrints * priceLarge) / 100;
                        result.setText("The order cost is " + currency.format(totalCost));
                    }
                    else {
                        Toast.makeText(MainActivity.this,
                                "Please select print size.",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "Please enter a number less than 50.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}