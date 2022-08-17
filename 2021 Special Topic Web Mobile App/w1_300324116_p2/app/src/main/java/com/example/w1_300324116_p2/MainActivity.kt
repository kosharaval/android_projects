package com.example.w1_300324116_p2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var loanAmount: Double = 0.0
        var year: Double = 0.0
        var interestRate: Double = 5.000
        var monthlyPayment:Double = 0.00
        var totalPayment:Double = 0.00

        var stringInterestRate: String = "Interest Rate \n"
        var stringMonthlyPayment: String = "Monthly Payment \n"
        var stringTotalPayment: String = "Total Payment \n"

        val textViewInterestRate = findViewById<TextView>(R.id.textViewInterestRate)
        val textViewMonthlyPayment = findViewById<TextView>(R.id.textViewMonthlyPayment)
        val textViewTotalPayment = findViewById<TextView>(R.id.textViewTotalPayment)

        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        val editTextYear = findViewById<EditText>(R.id.editTextYear)

        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)

        buttonCalculate.setOnClickListener{

            loanAmount = editTextAmount.getText().toString().toDouble()
            year = editTextYear.getText().toString().toDouble()

            do{

                monthlyPayment = (loanAmount * interestRate)/(1 - (1/Math.pow((1+interestRate),year*12)))
                totalPayment = monthlyPayment * year * 12

                stringInterestRate = stringInterestRate + String.format("%.3f", interestRate) + "\n"
                stringMonthlyPayment  = stringMonthlyPayment + String.format("%.3f", monthlyPayment) + "\n"
                stringTotalPayment = stringTotalPayment +  String.format("%.3f", totalPayment) + "\n"

                interestRate = interestRate + 0.125

            }while(interestRate < 8.000)

            textViewInterestRate.setText(stringInterestRate)
            textViewMonthlyPayment.setText(stringMonthlyPayment)
            textViewTotalPayment.setText(stringTotalPayment)
        }

    }
}