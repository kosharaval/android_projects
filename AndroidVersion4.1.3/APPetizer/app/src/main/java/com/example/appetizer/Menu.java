package com.example.appetizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu extends AppCompatActivity {
    int restId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = new Intent();
        intent = getIntent();
        restId = 0;

        if(intent != null){
            restId = (int) intent.getIntExtra("restId", 0);
        }

        DatabaseHelper dbh = new DatabaseHelper(this);
        ArrayList<Dish> dishes = dbh.GetMenu(restId);

        List<HashMap<String,String>> hmList = new ArrayList<HashMap<String,String>>();

        for (Dish d : dishes) {
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("title", d.getTitle());
            hm.put("desc", d.getDescription());
            hm.put("price", Double.toString(d.getPrice()));
            hmList.add(hm);
        }

        String[] from = {"title", "desc", "price"} ;
        int[] to = {R.id.txtItem, R.id.txtSubItem1, R.id.txtSubItem2};

        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), hmList, R.layout.listview_menu, from, to);

        final ListView lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent dishIntent = new Intent(Menu.this, MenuDish.class);
                int dishId = (int) id + 1;
                dishIntent.putExtra("dishId", dishId);
                dishIntent.putExtra("restId", restId);
                startActivity(dishIntent);
            }
        });

    }

    public void PlaceOrder(View view){
        DatabaseHelper dbh = new DatabaseHelper(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int orderId = sp.getInt("orderId", 0);
        //int userId = sp.getInt("userId", 0);

        if(orderId == 0){
            Toast.makeText(this,"Please select a dish first", Toast.LENGTH_LONG).show();
        }else{
            this.finish();
            startActivity(new Intent(Menu.this, Checkout.class));
        }
    }

}
