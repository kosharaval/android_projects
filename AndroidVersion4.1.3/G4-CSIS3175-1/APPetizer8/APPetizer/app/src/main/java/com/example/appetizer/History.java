package com.example.appetizer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class History extends AppCompatActivity {

    ArrayList<Orders> ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sp.getInt("userId", 0);

        DatabaseHelper dhb = new DatabaseHelper(this);
        //ArrayList<Orders> ordersList = dhb.GetOrders(userId);
        final ArrayList<Orders> ordersList = dhb.GetOrders(userId);

        List<HashMap<String,String>> hmList = new ArrayList<HashMap<String,String>>();

        for (Orders o : ordersList) {
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("id", "Order Number: " + Integer.toString(o.getOrderId()));
            hm.put("date", "Date: " + o.getDate());
            hm.put("price", "Total: " + o.getOrderTotal());
            hmList.add(hm);
        }

        String[] from = {"id", "date", "price"} ;
        int[] to = {R.id.txtItem, R.id.txtSubItem1, R.id.txtSubItem2};

        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), hmList, R.layout.listview_menu, from, to);

        ListView lvOrders = (ListView) findViewById(R.id.lvOrders);
        lvOrders.setAdapter(adapter);

        lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getSupportFragmentManager();
                OrderDetail fOrder = (OrderDetail)fm.findFragmentById(R.id.fragment);
                fOrder.LoadOrderDetail(ordersList.get(position));
            }
        });

    }
}
