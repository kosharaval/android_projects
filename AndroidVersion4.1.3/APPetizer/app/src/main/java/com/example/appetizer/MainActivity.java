package com.example.appetizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("orderId");
        editor.remove("userId");
        editor.commit();

        DatabaseHelper dbh = new DatabaseHelper(this);
        if(dbh.isEmptyTable()){
            dbh.PopulateRestaurant();
            dbh.PopulateDish();
            dbh.PopulateZip();
        }

    }
    public void ToLogin(View view){
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    public void ToSignup(View view){
        startActivity(new Intent(MainActivity.this, Signup.class));
    }
}
