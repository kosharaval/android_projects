package com.example.briskdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    int restId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
//changed
        Intent intent = getIntent();
        restId = 0;

        if(intent != null){
            restId = intent.getIntExtra("restId", 0);
        }

        DatabaseHelper dbh = new DatabaseHelper(this);
        ArrayList<Dish> dishes = dbh.GetMenu(restId);

        List<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();

        for (Dish d : dishes) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("title", d.getTitle());
            hm.put("desc", d.getDescription());
            hm.put("price", Double.toString(d.getPrice()));
            hmList.add(hm);
        }
        MenuAdapter madapter = new MenuAdapter(hmList);

        final ListView lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setAdapter(madapter);

        lvMenu.setOnItemClickListener((parent, view, position, id) -> {
            Intent dishIntent = new Intent(Menu.this, MenuDish.class);
            int dishId = (int) id + 1;
            dishIntent.putExtra("dishId", dishId);
            dishIntent.putExtra("restId", restId);
            startActivity(dishIntent);
        });


        btnPlaceOrder.setOnClickListener((View v) -> {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            int orderId = sp.getInt("orderId", 0);
            if(orderId == 0){
                Toast.makeText(this,"Please select a dish first", Toast.LENGTH_LONG).show();
            }else{
                this.finish();
                startActivity(new Intent(Menu.this, Checkout.class));
            }

        });

    }



}
