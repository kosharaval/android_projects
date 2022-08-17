package com.example.lec8dbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    SQLiteDatabase Studentsdb;
    StringBuilder outputText = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView txtViewDBResults = findViewById(R.id.txtViewDBResults);

        openDB(); //Opening the current students db
        outputText = new StringBuilder(); //creating a new empty string builder object

        outputText.append("\nDisplaying Grade Records...\n");
        browserGradeRecs(); //browsing grade records and appending to outputText

        txtViewDBResults.setText(outputText.toString());

    }
    private void browserGradeRecs(){
        //we are going to append to outputText after retrieving the cursor records
        try {
            String queryStr = "SELECT * from grades;";

            String headStr
                    = String.format("%-15s%-15s%-15s\n", "CourseId", "StudId", "StudGrade");
            outputText.append(headStr);

            Cursor cursor = Studentsdb.rawQuery(queryStr,null);
            if (cursor != null){
                Log.d("DB DEMO","Number of returned records: " + cursor.getCount());
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String eachRec = String.format("%-15s%-15s%-15.2f\n",
                            cursor.getString(0),cursor.getString(1),cursor.getDouble(2));
                    outputText.append(eachRec);
                    cursor.moveToNext();
                }
            }
        } catch (Exception ex){
            Log.d("DB DEMO",ex.getMessage());
        }

    }
    private void openDB(){
        try{
            Studentsdb = openOrCreateDatabase("Students.db",MODE_PRIVATE,null);
            Toast.makeText(this, "Database opened", Toast.LENGTH_SHORT).show();

        } catch (Exception ex){
            Log.d("DB DEMO","Database open error " + ex.getMessage());
        }
    }
}