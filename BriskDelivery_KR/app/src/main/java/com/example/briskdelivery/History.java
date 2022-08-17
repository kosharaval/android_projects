package com.example.briskdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Button backBtn = findViewById(R.id.backBtn);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sp.getInt("userId", 0);

        DatabaseHelper dhb = new DatabaseHelper(this);
        final ArrayList<Orders> ordersList = dhb.GetOrders(userId);

        List<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();

        for (Orders o : ordersList) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("id", "Order Number: " + o.getOrderId());
            hm.put("date", "Date: " + o.getDate());
            hm.put("price", "Total: " + o.getOrderTotal());
            hmList.add(hm);
        }

        HistoryAdapter adapter = new HistoryAdapter(hmList);

        ListView lvOrders =findViewById(R.id.lvOrders);
        lvOrders.setAdapter(adapter);

        lvOrders.setOnItemClickListener((parent, view, position, id) -> {
            Intent hisintent = new Intent( History.this,  OrderDetail.class);
            int orderId = ordersList.get(position).getOrderId();
            hisintent.putExtra("orderId", orderId);
            hisintent.putExtra("userId", userId);

            startActivity(hisintent);
        });

        backBtn.setOnClickListener((View v) -> {

            startActivity(new Intent(History.this, Buttons.class));

        });


    }
}
