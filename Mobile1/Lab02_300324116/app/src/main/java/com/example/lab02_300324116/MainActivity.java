package com.example.lab02_300324116;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnContact1 = (Button) findViewById(R.id.btnLastName1);

        btnContact1.setOnClickListener(new View.OnClickListener()
                               {
                                   @Override
                                   public void onClick(View v)
                                   {
                                       startActivity(new Intent(MainActivity.this,lastName1_Info.class));
                                   }
                               }
        );

        Button btnContact2 = (Button) findViewById(R.id.btnLastName2);

        btnContact2.setOnClickListener(new View.OnClickListener()
                               {
                                   @Override
                                   public void onClick(View v)
                                   {
                                       startActivity(new Intent(MainActivity.this,lastName2_Info.class));
                                   }
                               }
        );
    }
}