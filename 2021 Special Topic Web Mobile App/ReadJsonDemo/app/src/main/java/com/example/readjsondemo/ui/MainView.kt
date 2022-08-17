package com.example.readjsondemo.ui

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.example.readjsondemo.data.Employee
import com.example.readjsondemo.data.Employee2
import com.example.readjsondemo.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainView (ctx : Context, fileName: String, txtResult : TextView){
    private val myType = Types.newParameterizedType(List::class.java, Employee::class.java)
    private val myType2 = Types.newParameterizedType(List::class.java, Employee2::class.java)

    init {
        Log.i("MainView", "init")
        val text = FileHelper.getDataFromAssets(ctx,fileName)
        Log.i("MainView", text)

        /*
            parsing the the json using Moshi

            add the following in build.gradle
            implementation 'com.squareup.moshi:moshi:1.8.0'
            implementation "com.squareup.moshi:moshi-adapters:1.8.0"
            implementation "com.squareup.moshi:moshi-kotlin:1.8.0"
         */

        // Using data class Employee
//        val moshi: Moshi = Moshi.Builder().build()
//        val adapter : JsonAdapter<List<Employee>> = moshi.adapter(myType)
//        val empList = adapter.fromJson(text)
//
//        // ?: Elvis Operator
//        for (e in empList ?: emptyList() ) {
//            Log.i(this.toString(), "${e.first_name} - ${e.last_name}")
//        }

        // Using data class Employee2
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<Employee2>> = moshi.adapter(myType2)

        val empList = adapter.fromJson(text)

        // ?: Elvis Operator
        txtResult.text = ""
        for (e in empList ?: emptyList() ) {
            Log.i(this.toString(), "${e.fn} - ${e.last_name}")
            txtResult.append("${e.fn} - ${e.last_name} \n")
        }


    }
}