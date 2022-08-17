package com.example.appetizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantList extends AppCompatActivity {

    ArrayList<Restaurant> restList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sp.getInt("userId", 0);

        DatabaseHelper dhb = new DatabaseHelper(this);
        restList = dhb.GetRestautantList(userId);

        List<HashMap<String,String>> hmList = new ArrayList<HashMap<String,String>>();

        for (Restaurant r : restList) {
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", r.getRestName());
            hm.put("images", Integer.toString(r.getRestImage()));
            hmList.add(hm);
        }

        String[] from = {"images", "txt"} ;
        int[] to = {R.id.imgRestList, R.id.tvRestList};

        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), hmList, R.layout.listview_layout, from, to);

        ListView lvRestaurant = (ListView) findViewById(R.id.lvRestaurant);
        lvRestaurant.setAdapter(adapter);

        lvRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent menuIntent = new Intent(RestaurantList.this, Menu.class);
                //int restId = (int) id + 1;
                int restId = restList.get(position).getRestId();
                menuIntent.putExtra("restId", restId);
                startActivity(menuIntent);
            }
        });
    }
}
