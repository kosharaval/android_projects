nai
        package com.example.japneets_3175final;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    SQLiteDatabase Coursesdb;
    List<Course> courses;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonViewSummary = findViewById(R.id.buttonViewSummary);

        createDB();
        createTables();

        courses = ReadCSVCourses();
        GridView gridViewCourses = findViewById(R.id.gridViewCourses);
        gridViewCourses.setAdapter(new CourseGridViewAdapter(courses));

        gridViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                updateConcertTickets(courses.get(position).getCourseName());
            }
        });

        buttonViewSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PurchaseSummaryActivity.class);
                startActivity(intent);
            }
        });
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Course> ReadCSVCourses(){

        List<Course> courseList = new ArrayList<>();


        InputStream inputStream = getResources().openRawResource(R.raw.courses);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            String csvLine;
            String headLine = reader.readLine();
            int CourseID = 0;

            //CourseDate,CourseName,CoursePic,CoursePrice,CourseDiscount
            while((csvLine = reader.readLine())!=null){

                CourseID++;
                String[] col = csvLine.split(",");  //comma separated String[]
                String CourseDate = col[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
                LocalDate date = LocalDate.parse(CourseDate,formatter);

                String CourseName = col[1];

                String CoursePic = col[2]; //this is the drawable resource name
                int ConcertDrawable = getResources().getIdentifier(CoursePic,"drawable",getPackageName());


                double CoursePrice = Double.parseDouble(col[3]);
                int CourseDiscount = Integer.parseInt(col[4]);

                Course concert = new Course( CourseID, date,  CourseName,  CoursePic, ConcertDrawable,  CoursePrice, CourseDiscount);
                courseList.add(concert);

                addCourseInfo(CourseDate,CourseName,CoursePic,CoursePrice,0,CourseDiscount);
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
        return courseList;
        
    }
    
    private void createDB(){
        try{
            Coursesdb = openOrCreateDatabase("Courses.db",MODE_PRIVATE,null);
            Toast.makeText(this, "Database ready", Toast.LENGTH_SHORT).show();
        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
    }

    private void createTables(){
        try {
            String dropCourseTableCmd = "DROP TABLE IF EXISTS " + "courses;";
            String createCourseTableCmd = "CREATE TABLE courses " +
                    "(coursedate TEXT, coursename TEXT, coursedrawablename TEXT,courseprice REAL,coursenumsessions INTEGER, coursediscount INTEGER );";
            Coursesdb.execSQL(dropCourseTableCmd);
            Coursesdb.execSQL(createCourseTableCmd);

        } catch (Exception ex){

        }
    }

    private void addCourseInfo(String coursedate, String coursename, String coursedrawablename, double courseprice, int coursenumsessions, int coursediscount ){
        long result;
        ContentValues val = new ContentValues();
        val.put("coursedate",coursedate);
        val.put("coursename",coursename);
        val.put("coursedrawablename",coursedrawablename);
        val.put("courseprice",courseprice);
        val.put("coursenumsessions",coursenumsessions);
        val.put("coursediscount",coursediscount);

        try{
            result = Coursesdb.insert("courses",null,val);
            if (result != -1){
                Log.d("DB DEMO", "rowid = " + result + " inserted course with coursename " + coursename);
            } else{
                Log.d("DB DEMO","Error inserting course with coursename " + coursename);
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Error adding student for " + coursename);
        }
    }

    private void updateConcertTickets(String coursename) {
        String queryStr = "SELECT * " +
                "FROM courses WHERE coursename = ?;";
        try {
            Cursor cursor = Coursesdb.rawQuery(queryStr, new String[]{coursename});

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String courseName = cursor.getString(1);
                    int numSession = cursor.getInt(4);
                    ContentValues val = new ContentValues();
                    val.put("coursenumsessions", numSession + 1);
                    Coursesdb.update("courses", val,
                            "coursename = ?", new String[]{coursename});
                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            Log.d("DB DEMO", "Updating courses grade records failed.." + ex.getMessage());
        }

    }

}