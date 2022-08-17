package com.example.w3_300324116_p1

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.w3_300324116_p1.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {

    private val myType = Types.newParameterizedType(List::class.java, CarOwner::class.java)
    private val myFileName = "mydata_300324116.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var carArrayList = ArrayList<Car>();

        val textViewDisplayFirstName = findViewById<TextView>(R.id.textViewDisplayFirstName)
        val textViewDisplayLastName = findViewById<TextView>(R.id.textViewDisplayLastName)
        val textViewDisplayCarModel = findViewById<TextView>(R.id.textViewDisplayCarModel)
        val spinnerSortBy =  findViewById<Spinner>(R.id.spinnerSortBy)
        val buttonSort =  findViewById<Button>(R.id.buttonSort)

        var textViewFirstNameString : String = ""
        var textViewLastNameString : String = ""
        var textViewCarModelString : String = ""

        val text = FileHelper.getDataFromAssets(this,myFileName)

        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val adapter : JsonAdapter<List<CarOwner>> = moshi.adapter(myType)
        val companyList = adapter.fromJson(text)

        for (e in companyList ?: emptyList() ) {
            for (d in e.cars) {
                var car:Car = Car(e.id.toString().toInt(),e.first_name,e.last_name,d.item)
                carArrayList.add(car)
                textViewFirstNameString += e.first_name + "\n"
                textViewLastNameString += e.last_name + "\n"
                textViewCarModelString += d.item + "\n"
            }
        }

        textViewDisplayFirstName.setText(textViewFirstNameString)
        textViewDisplayLastName.setText(textViewLastNameString)
        textViewDisplayCarModel.setText(textViewCarModelString)

        buttonSort.setOnClickListener{

            var spinnerSelection = spinnerSortBy.selectedItem
            if(spinnerSelection != "--Select Please--"){
                textViewFirstNameString = ""
                textViewLastNameString = ""
                textViewCarModelString = ""
            }
            when(spinnerSelection){
                "First Name" -> {
                    var sortedByTitle = carArrayList.sortedBy{it.firstName}
                    for(t in sortedByTitle) {
                        textViewFirstNameString += t.firstName + "\n"
                        textViewLastNameString += t.lastName + "\n"
                        textViewCarModelString += t.carModel + "\n"
                    }
                }
                "Last Name" ->{
                    var sortedByTitle = carArrayList.sortedBy{it.lastName}
                    for(t in sortedByTitle) {
                        textViewFirstNameString += t.firstName + "\n"
                        textViewLastNameString += t.lastName + "\n"
                        textViewCarModelString += t.carModel + "\n"
                    }

                }
                "Car Model" ->{
                    var sortedByTitle = carArrayList.sortedBy{it.carModel}
                    for(t in sortedByTitle) {
                        textViewFirstNameString += t.firstName + "\n"
                        textViewLastNameString += t.lastName + "\n"
                        textViewCarModelString += t.carModel + "\n"
                    }

                }
                else ->{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Alert")
                    builder.setMessage("Please select a property by which you want to Sort")
                    builder.setPositiveButton("OK"){dialogInterface, which->
                        "Clicked OK"
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.show()
                }
            }
            textViewDisplayFirstName.setText(textViewFirstNameString)
            textViewDisplayLastName.setText(textViewLastNameString)
            textViewDisplayCarModel.setText(textViewCarModelString)
        }
    }
}