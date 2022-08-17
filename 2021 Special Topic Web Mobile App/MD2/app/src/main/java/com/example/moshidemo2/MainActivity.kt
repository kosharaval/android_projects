package com.example.moshidemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moshidemo2.databinding.ActivityMainBinding
import com.example.moshidemo2.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/*
    The Steps
    1. Copy the .json file into your assets folder
    2. add the following in buid.gradle (Module: ... )
        under plugins
            id 'kotlin-kapt'
            id 'kotlin-android-extensions'
        under dependencies
            implementation 'com.squareup.moshi:moshi:1.8.0'
            kapt 'com.squareup.moshi:moshi-kotlin-codegen:1.8.0'
            implementation "com.squareup.moshi:moshi-kotlin:1.8.0"
    3. Create a class to hold the data
       I created a class called Company.
    4. Create the myType (this can be any name you want)
    5. Read the contents of the file
    6. Create the Moshi adapter
 */
class MainActivity : AppCompatActivity() {

    // Layout name: activity_main.xml
    // therefore, the binding name would be ActivityMainBinding
    // This is the latest approach to viewBinding
    private lateinit var binding: ActivityMainBinding

    // This is Step 4
    private val myType = Types.newParameterizedType(List::class.java, Company::class.java)

    private val FILENAME = "TheData.json"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Step 5 - reads the data from the .json file
        val text = FileHelper.getDataFromAssets(this,FILENAME)

        // Step 6
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
 
        val adapter : JsonAdapter<List<Company>> = moshi.adapter(myType)

        // Step 7 : give the data to the adapter
        val companyList = adapter.fromJson(text)

        // ?: Elvis Operator
        binding.displayTxt.text = ""
        for (e in companyList ?: emptyList() ) {

            binding.displayTxt.append("${e.id}  \n")
            // e.Companies refers to the MutableList<Department>
            for (d in e.Companies) {

                binding.displayTxt.append(String.format("   %s \n","${d.dept}"))
            }
            binding.displayTxt.append("\n")
        }





    }
}