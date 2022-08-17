package com.example.readjsondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.readjsondemo.ui.MainView
import kotlinx.android.synthetic.main.activity_main.*

/*
    Demo code on
    1. how to read JSon file
    2. how to use Moshi to parse the JSon
    3. dummy data was generated using https://www.mockaroo.com/
       assets\employee.json

    Task edit the code to display the result on the mobile device instead of using Log.i
 */

class MainActivity : AppCompatActivity() {
    private val FILENAME = "employee.json"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mv = MainView(this, FILENAME,txt)
    }
}