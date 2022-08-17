package com.example.briskdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Buttons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        Button rListBtn = findViewById(R.id.rListBtn);
        Button oHisbtn = findViewById(R.id.oHisbtn);
        Button viewMap = findViewById(R.id.viewMap);
        Button viewProfile = findViewById(R.id.viewProfile);


        rListBtn.setOnClickListener((View v) -> {

            startActivity(new Intent(Buttons.this, RestaurantList.class));

        });


        oHisbtn.setOnClickListener((View v) -> {

            startActivity(new Intent(Buttons.this, History.class));

        });

        viewMap.setOnClickListener((View v) -> {

            startActivity(new Intent(Buttons.this, MapsActivity.class)
            );

        });

        viewProfile.setOnClickListener((View v) -> {

            startActivity(new Intent(Buttons.this, ProfileActivity.class)
            );

        });
    }
}