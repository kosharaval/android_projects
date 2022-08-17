package com.example.myfragmentviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfragmentviewmodel.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()

            // R.id.containter : go to main_activity.xml
            /*
                Analyze the following in sequence
                1. MainFragment
                2. main_fragment.xml
                3. MainViewModel
             */

        }
    }
}