package com.example.briskdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

public class MenuDish extends AppCompatActivity {

    int dishId = 0;
    int restId = 0;
    TextView tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dish);

        Intent intent = getIntent();
        Button btnAddToOrder = findViewById(R.id.btnAddToOrder);


        if(intent != null){
            dishId = intent.getIntExtra("dishId", 0);
            restId =  intent.getIntExtra("restId", 0);
        }

        DatabaseHelper dbh = new DatabaseHelper(this);
        Dish dish = dbh.GetDish(dishId, restId);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(dish.getTitle());
        TextView tvDesc = findViewById(R.id.tvDesc);
        tvDesc.setText(dish.getDescription());
         tvPrice = findViewById(R.id.tvPrice);
        tvPrice.setText(Double.toString(dish.getPrice()));

        btnAddToOrder.setOnClickListener((View v) -> {

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
               // TextView pricetxt = findViewById(R.id.tvPrice);
                double price = Double.parseDouble(tvPrice.getText().toString()) * qty;

                dbh.addItem(new Item(orderId, dishId, qty,price, restId));
                Toast.makeText(this, "Item added to order", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MenuDish.this, Menu.class);
                i.putExtra("restId", restId);
                this.finish();
                startActivity(i);
            }

        });

    }


}
