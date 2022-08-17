package com.example.mynavcompdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
            1. loads activity_main.xml
            2. Inside the activity_main.xml, you will find a reference to nav_graph.xml
               a. app:navGraph="@navigation/nav_graph
               b. Inside nav_graph.xml, it "calls" MainFragment
                  // nav_graph contains a fragment pointing to main_fragment
                  android:name="com.example.mynavcompdemo1.MainFragment"

                  main_fragment.xml is associated with MainFragment.kt and contains RecyclerView
         */
    }
}