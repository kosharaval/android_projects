package com.example.appetizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuDish extends AppCompatActivity {

    int dishId = 0;
    int restId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dish);

        Intent intent = new Intent();
        intent = getIntent();

        if(intent != null){
            dishId = (int) intent.getIntExtra("dishId", 0);
            restId = (int) intent.getIntExtra("restId", 0);
        }

        DatabaseHelper dbh = new DatabaseHelper(this);
        Dish dish = dbh.GetDish(dishId, restId);

        //show dish
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(dish.getTitle());
        TextView tvDesc = findViewById(R.id.tvDesc);
        tvDesc.setText(dish.getDescription());
        TextView tvPrice = findViewById(R.id.tvPrice);
        tvPrice.setText(Double.toString(dish.getPrice()));

    }

    public void AddToOrder(View view){
        DatabaseHelper dbh = new DatabaseHelper(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int orderId = sp.getInt("orderId", 0);
        int userId = sp.getInt("userId", 0);

        if(orderId == 0){
            orderId = dbh.addOrders(new Orders(userId, false));
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("orderId", orderId);
            editor.commit();
        }
        EditText edQty = (EditText)findViewById(R.id.edQty);

        if(edQty.getText().toString().equals("")){
            Toast.makeText(this, "Please insert quantity", Toast.LENGTH_SHORT).show();
        }else{

        int qty = Integer.parseInt(edQty.getText().toString());
        TextView tvPrice = findViewById(R.id.tvPrice);
        double price = Double.parseDouble(tvPrice.getText().toString()) * qty;
        EditText special = findViewById(R.id.edSpecialComment);

        dbh.addItem(new Item(orderId, dishId, qty,price, special.getText().toString(), restId));
        Toast.makeText(this, "Item added to order", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MenuDish.this, Menu.class);
        i.putExtra("restId", restId);
        this.finish();
        startActivity(i);
        }
    }
}
