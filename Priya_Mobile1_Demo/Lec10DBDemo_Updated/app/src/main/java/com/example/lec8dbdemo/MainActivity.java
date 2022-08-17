package com.example.lec8dbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
        createTables(); //creating tables

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

            //listViewStudents.setAdapter(new StudentAdapter(Students)); //adapter data comes from CSV

            //We are adding from row 2 of the CSV file into the Database
            for(int i = 1; i < Students.size(); i++){
                String id = Students.get(i)[0];
                String name = Students.get(i)[1];
                String dept = Students.get(i)[2];

                addStudentProfile(id, name, dept); //adding the student record to the students table
            }

            List<String[]> studentRecsDB = browseStudentRecs();
            //use a log to display number of items in the DB adapter data
            listViewStudents.setAdapter(new StudentAdapter(studentRecsDB)); //adapter data comes from DB

            //adding grade records
            addStudentGrade("CSIS1280","D312345",98.8);
            addStudentGrade("CSIS3175","D300123",89.7);
            addStudentGrade("CSIS3280","D300124",86.5); //this student does not exist in the students table - will not be added to grades table
            addStudentGrade("CSIS3275","D312345",58.3); //this is another course for the first student
            addStudentGrade("CSIS3275","D312345",95.6); //another record for the same student, course - will not be added

            //after all data has been inserted into db
            //moving to second activity to show db retrieval and
            //output in TextView using StringBuilder in the Second Activity
            startActivity(new Intent(MainActivity.this,SecondActivity.class));

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

    private void createTables(){
        try {
          String setPRAGMAForeignKeysOn = "PRAGMA foreign_keys=ON;";
          String dropStudentsTableCmd = "DROP TABLE IF EXISTS " + "students;";
          String dropGradesTableCmd =  "DROP TABLE IF EXISTS " + "grades;";
          String createStudentsTableCmd = "CREATE TABLE students "
                  + "(studentid TEXT PRIMARY KEY, name TEXT, dept TEXT);";
          String createGradesTableCmd = "CREATE TABLE grades " +
                  "(courseid TEXT, studentid TEXT, grade REAL, PRIMARY KEY (courseid, studentid), " +
                  "FOREIGN KEY(studentid) REFERENCES students(studentid));";
          StudentsDb.execSQL(setPRAGMAForeignKeysOn);
          StudentsDb.execSQL(dropGradesTableCmd); //dropping grades table first
          StudentsDb.execSQL(dropStudentsTableCmd); //dropping students table after
          StudentsDb.execSQL(createStudentsTableCmd); //creating students table
          StudentsDb.execSQL(createGradesTableCmd); //creating grades table

        } catch (Exception ex){

        }
    }

    private void addStudentProfile(String id, String name, String dept){
        long result;
        ContentValues val = new ContentValues();
        val.put("studentid","D" + id); //just adding a D in front of the id before inserting into DB
        val.put("name",name);
        val.put("dept",dept);
        try{
            result = StudentsDb.insert("students",null,val);
            if (result != -1){
                Log.d("DB DEMO", "rowid = " + result + " inserted student with id " + id);
            } else{
                Log.d("DB DEMO","Error inserting student with id " + id);
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Error adding student for " + id);
        }
    }

    private List<String[]> browseStudentRecs(){
        List<String[]> StudentList = new ArrayList<>();

        //Create a header String[] and add it to the list
        //this list will then be passed into the student adapter
        //to be put in the listview
        String[] headRec = new String[3];
        headRec[0] = "StudentId";
        headRec[1] = "StudName";
        headRec[2] = "Dept";

        StudentList.add(headRec); //added header String[] to the list

        String queryStr = "SELECT * FROM students;";

        try{
            Cursor cursor = StudentsDb.rawQuery(queryStr,null);
            if (cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String[] eachRecArray = new String[3];
                    eachRecArray[0] = cursor.getString(0);
                    eachRecArray[1] = cursor.getString(1);
                    eachRecArray[2] = cursor.getString(2);

                    StudentList.add(eachRecArray);
                    cursor.moveToNext();
                }
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Querying student recs error " + ex.getMessage());
        }


        return StudentList;
    }

    private void addStudentGrade(String courseId, String studentId, double grade){
        long result = 0;
        ContentValues val = new ContentValues();
        val.put("courseid",courseId);
        val.put("studentid",studentId);
        val.put("grade",grade);

        try{
            result = StudentsDb.insertOrThrow("grades",null,val);
            if (result != -1){
                Log.d("DB DEMO","Added grade for student "
                        + studentId + " for course " + courseId);
            } else {
                Log.d("DB DEMO","Error adding grade for student "
                        + studentId + " in course " + courseId);
            }
        } catch (SQLiteException ex){
            Log.d("DB DEMO",ex.getMessage()
                    + " ..Exception..for student " + studentId + " in course " + courseId);
        }
    }

}