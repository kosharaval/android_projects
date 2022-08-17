package com.example.appetizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void ToRestaurant(View view){
        startActivity(new Intent(Options.this, RestaurantList.class));
    }

    public void ToHistory(View view){
        startActivity(new Intent(Options.this, History.class));
    }

    public void ToAccount(View view){
        startActivity(new Intent(Options.this, Account.class));
    }

    public void ToInvite(View view) {startActivity(new Intent(Options.this, Invite.class));}
}
