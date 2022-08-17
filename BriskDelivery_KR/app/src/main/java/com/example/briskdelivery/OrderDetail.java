package com.example.briskdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;


public class OrderDetail extends AppCompatActivity {

    int orderId =0;
    int userId =0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent  = getIntent();
        orderId = 0;

        if(intent != null){
            orderId =  intent.getIntExtra("orderId", 0);
            userId =  intent.getIntExtra("userId", 0);

        }

        DatabaseHelper dbh = new DatabaseHelper(this);
        ArrayList<Orders> orders = dbh.GetOrders(userId);

        List<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();

        for (Orders d : orders) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hmList.add(hm);
        }



        MenuAdapter madapter = new MenuAdapter(hmList);

        final ListView lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setAdapter(madapter);



    }


}
