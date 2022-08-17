package com.example.beerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import com.example.beerapp.R;

public class FlashActivity extends AppCompatActivity {

    private static int TIME_OUT= 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(com.example.beerapp.activities.FlashActivity.this, com.example.beerapp.activities.MainActivity.class));
                finish(); // done
            }
        } , TIME_OUT);
    }
}
