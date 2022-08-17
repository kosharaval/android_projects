package com.example.w1_300324116_p1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var positiveCount: Int = 0
        var negativeCount:Int = 0
        var total:Float = 0.0f
        var average:Float = 0.0f
        var numberCount:Int  = 0
        var stringResult:String = ""

        var editTextNumbers = findViewById(R.id.editTextNumbers) as EditText
        var textViewResult = findViewById(R.id.textViewResult) as TextView
        var buttonCalculator = findViewById(R.id.buttonCalculator) as Button

        var stringOfNumbers = editTextNumbers.getText().toString()

        buttonCalculator.setOnClickListener{

            textViewResult.setText("")
            var stringOfNumbers = editTextNumbers.getText().toString()
            if(!stringOfNumbers.isEmpty()){
                val arrayofNumbers = stringOfNumbers.split(" ")

                for(value in arrayofNumbers){

                    if(value.toInt() !=0){
                        numberCount += 1
                    }
                    if(value.toInt() > 0){
                        positiveCount += 1
                    }
                    if(value.toInt() < 0){
                        negativeCount +=  1
                    }
                    total += value.toInt()
                }
                average = total / numberCount

                stringResult += "The number of postives is " + positiveCount + "\n"
                stringResult += "The number of negatives is " + negativeCount + "\n"
                stringResult += "The total is " + total + "\n"
                stringResult += "The average is " + String.format("%.2f", average) + "\n"

                textViewResult.setText(stringResult)

            }
            else{
                Toast.makeText(this@MainActivity, "Please enter numbers." + stringOfNumbers, Toast.LENGTH_SHORT).show()
            }

        }

        
    }
}