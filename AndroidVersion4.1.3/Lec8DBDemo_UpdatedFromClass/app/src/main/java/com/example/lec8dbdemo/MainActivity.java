package com.example.lec8dbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase StudentsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextCourseNum = findViewById(R.id.editTextCourseNum);
        Button btnSaveCourseNumImportRecs = findViewById(R.id.btnSaveCourseNumImportRecs);
        ListView listViewStudents = findViewById(R.id.listViewStudents);

        createDB(); //create the database

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPreferences.contains("COURSENUM")){
            String courseNum = sharedPreferences.getString("COURSENUM","");
            editTextCourseNum.setText(courseNum);
        }

        btnSaveCourseNumImportRecs.setOnClickListener((View v) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("COURSENUM",editTextCourseNum.getText().toString());
            editor.commit(); //commit the changes to the shared preference edit that we just did

            //Call ReadCSVStudents here
            List<String[]> Students = ReadCSVStudents();

            Log.d("DB DEMO",Students.size() + " Items in the file"); //just for testing if the file read was right

            listViewStudents.setAdapter(new StudentAdapter(Students));
        });


    }

    private List<String[]> ReadCSVStudents(){
        List<String[]> studentsList = new ArrayList<>();

        //populate the list
        InputStream inputStream = getResources().openRawResource(R.raw.students);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{
           String csvLine;
           while((csvLine = reader.readLine()) != null){
               String[] eachStudent = csvLine.split(",");
               studentsList.add(eachStudent); //adding 3-element string array to the list
           }
        } catch (IOException ex){
            throw new RuntimeException("Error reading CSV file " + ex);
        } finally {
            try{
                inputStream.close();
            } catch (IOException ex){
                throw new RuntimeException("Error closing input stream " + ex);
            }
        }
        return studentsList;
    }

    private void createDB(){
        try{
            StudentsDb = openOrCreateDatabase("Students.db",MODE_PRIVATE,null);
            Toast.makeText(this, "Database ready", Toast.LENGTH_SHORT).show();
        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
    }
}