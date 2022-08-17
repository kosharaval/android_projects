package com.example.japneets_3175final;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PurchaseSummaryActivity extends AppCompatActivity {

    List<Course> courseList = new ArrayList<>();
    double total = 0;
    TextView textViewResult;
    SQLiteDatabase CourseDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_summary);

        textViewResult = findViewById(R.id.textViewResult);
        openDB();
        browseCourse();

        ListView listViewPurchases = findViewById(R.id.listViewPurchases);
        listViewPurchases.setAdapter(new PurchaseListViewAdapter(courseList));

        DecimalFormat df = new DecimalFormat("$#.##");
        textViewResult.setText("Grand Total: " + df.format(total));
    }

    private void openDB(){
        try{
            CourseDb = openOrCreateDatabase("Courses.db",MODE_PRIVATE,null);
            Toast.makeText(this, "Database opened", Toast.LENGTH_SHORT).show();

        } catch (Exception ex){
            Log.d("DB DEMO","Database open error " + ex.getMessage());
        }
    }

    private void browseCourse(){
        double subTotal = 0;
        String queryStr = "SELECT * FROM courses " +
                "WHERE coursenumsessions > 0 ;";
        try{
            Cursor cursor = CourseDb.rawQuery(queryStr,null);

            if (cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){

                    String courseName = cursor.getString(1);
                    Double coursePrice = Double.parseDouble(cursor.getString(3));
                    int numberSession = Integer.parseInt(cursor.getString(4));
                    int courseDiscount = Integer.parseInt(cursor.getString(5));
                    if(courseDiscount == 1)
                    {
                        double discount = coursePrice * 10/100 ;
                        subTotal = (numberSession * coursePrice) - discount;
                        total = total + subTotal;
                    }
                    else {
                        subTotal = numberSession * coursePrice;
                        total = total + subTotal;
                    }


                    Course course =  new Course(courseName,coursePrice,courseDiscount,numberSession,subTotal);
                    courseList.add(course);

                    cursor.moveToNext();
                }
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Error in joining student name and grade" + ex.getMessage());
        }
    }
}