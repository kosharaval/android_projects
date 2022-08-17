package com.example.briskdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantList extends AppCompatActivity {

    ArrayList< Restaurant> restList;

    List<String> list = new ArrayList<>();
    List<Integer> pic = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
         DatabaseHelper dhb = new  DatabaseHelper(this);
        restList = dhb.GetRestautantList();

        List<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();

        for ( Restaurant r : restList) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt", r.getRestName());
            hm.put("images", Integer.toString(r.getRestImage()));
            hmList.add(hm);
            list.add(r.getRestName());
            pic.add(r.getRestImage());
        }

        ImageAdapter adapter = new ImageAdapter(list,pic);
      ListView lvRestaurant = findViewById(R.id.lvRestaurant);
       lvRestaurant.setAdapter(adapter);

        lvRestaurant.setOnItemClickListener((parent, view, position, id) -> {
            Intent menuIntent = new Intent( RestaurantList.this,  Menu.class);
            int restId = restList.get(position).getRestId();
            menuIntent.putExtra("restId", restId);
            startActivity(menuIntent);
        });
    }
}
