package com.example.japneets_3175finalpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    SQLiteDatabase ConcertDb;
    List<Concert> concertList = new ArrayList<>();
    double total = 0;
    TextView textViewShowTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        textViewShowTotal = findViewById(R.id.textViewShowTotal);

        openDB();

        browseConcert();

        GridView gridViewConcertCart = findViewById(R.id.gridViewConcertCart);
        gridViewConcertCart.setAdapter(new CartGridViewAdapter(concertList));

        DecimalFormat df = new DecimalFormat("$#.##");
        textViewShowTotal.setText("Cart Total: " + df.format(total));

    }

    private void openDB(){
        try{
            ConcertDb = openOrCreateDatabase("Concert.db",MODE_PRIVATE,null);
            Toast.makeText(this, "Database opened", Toast.LENGTH_SHORT).show();

        } catch (Exception ex){
            Log.d("DB DEMO","Database open error " + ex.getMessage());
        }
    }

    private void browseConcert(){
        String queryStr = "SELECT * FROM concerts " +
                "WHERE concerttix > 0 ;";
        try{
            Cursor cursor = ConcertDb.rawQuery(queryStr,null);

            if (cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){

                    String concertName = cursor.getString(2);
                    Double concertPrice = Double.parseDouble(cursor.getString(4));
                    int concertNumber = Integer.parseInt(cursor.getString(5));
                    double subtotal = concertNumber * concertPrice;

                    total = subtotal + total;

                    Concert concert =  new Concert(concertName,concertPrice,concertNumber,subtotal);
                    concertList.add(concert);

                    cursor.moveToNext();
                }
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Error in joining student name and grade" + ex.getMessage());
        }
    }
}