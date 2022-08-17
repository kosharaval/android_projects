package com.example.lec8dbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

        outputText.append("Displaying student join grade recs...\n");
        BrowseJoinStudGrades(); //browse student join grade recs getting name and grade joined on studentid

        outputText.append("Displaying student join high grades...\n");
        BrowseJoinStudHighGrades(90);

        outputText.append("Update grades for student and for one course..\n");
        CheckAndUpdateGrade("CSIS1280","D312345"); //inc the grade for this student in this course by 10%

        outputText.append("Update all grades for a given student...\n");
        UpdateGradeForStud("D312345");

        outputText.append("Deleting a student/course record from grades...\n");
        DeleteGrade("CSIS3175","D300123");

        outputText.append("Displaying grades..after updates and delete...\n");
        browserGradeRecs();

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
    private void BrowseJoinStudGrades(){
        String queryStr = "SELECT name, grade FROM students " +
                "INNER JOIN grades ON students.studentid = grades.studentid;";
        try{
            Cursor cursor = Studentsdb.rawQuery(queryStr,null);
            String headRec = String.format("%-15s%-15s\n","StudName","StudGrade");
            outputText.append(headRec);

            if (cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    //column name corresponds to column 0 and
                    //grade corresponds to column 1 in this query cursor return

                    String eachRec = String.format("%-15s%-15.2f\n",
                            cursor.getString(0),cursor.getDouble(1));
                    outputText.append(eachRec);
                    cursor.moveToNext(); //move to the next record in the cursor
                }
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Error in joining student name and grade" + ex.getMessage());
        }
    }
    private void BrowseJoinStudHighGrades(int cutOffGrade){
        String queryStr = "SELECT name, grade FROM students INNER JOIN grades " +
                            "ON students.studentid=grades.studentid WHERE grade > ?;";
        try{
            Cursor cursor = Studentsdb.rawQuery(queryStr,new String[]{String.valueOf(cutOffGrade)});
            String headRec = String.format("%-15s%-15s\n","Name","Grade");
            outputText.append(headRec);

            if (cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String eachRec = String.format("%-15s%-15.2f\n",
                            cursor.getString(0),cursor.getDouble(1));
                    outputText.append(eachRec);
                    cursor.moveToNext();
                }
            }
        } catch (Exception ex){
            Log.d("DB DEMO", "Error querying high grades.."+ex.getMessage());
        }
    }

    private boolean CheckAndUpdateGrade(String courseId, String studId){
        String queryStr = "SELECT grade FROM grades WHERE courseid = ? AND studentid = ?;";
        try{
            Cursor cursor = Studentsdb.rawQuery(queryStr,new String[]{courseId,studId});
            if (cursor != null && cursor.getCount() == 1){
                cursor.moveToFirst();
                double oldGrade = cursor.getDouble(0);
                ContentValues val = new ContentValues();
                val.put("grade",oldGrade*1.1); //new grade is 10% more than the old grade
                Studentsdb.update("grades",val,
                        "courseid = ? AND studentid = ?",new String[]{courseId,studId});
                Log.d("DB DEMO","Updated grade for " + studId + " for course " + courseId);
                return true;
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Error updating records.." + ex.getMessage());
        }

        return false;
    }

    private int UpdateGradeForStud(String studId){
        int numRecs = 0;
        String queryStr = "SELECT courseid, studentid, grade " +
                                    "FROM grades WHERE studentid = ?;";
        try{
            Cursor cursor = Studentsdb.rawQuery(queryStr, new String[]{studId});

            if (cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String courseId = cursor.getString(0);
                    double oldGrade = cursor.getDouble(2); //grade is the third column in this query return
                    ContentValues val = new ContentValues();
                    val.put("grade",oldGrade*1.1); //10% inc on old grade
                    Studentsdb.update("grades",val,
                            "courseid = ? AND studentid = ?",new String[]{courseId, studId});
                    numRecs++;
                    cursor.moveToNext();
                }
                //numRecs = cursor.getCount(); //you can also use getCount() of cursor to return the number of recs that match this stud id

            }
        } catch (Exception ex){
            Log.d("DB DEMO","Updating student grade records failed.."+ex.getMessage());
        }

        return numRecs;
    }
    private void DeleteGrade(String courseId, String studId){
        try{
            int result = Studentsdb.delete("grades",
                    "courseid = ? AND studentid = ?",new String[]{courseId, studId});
            if (result > 1) {
                Log.d("DB DEMO","Multiple grades for " + studId + " for course " + courseId);
            } else if (result == 1){
                Log.d("DB DEMO", "Found and deleted one record for "
                                                        + studId + " for course " + courseId);
            } else{
                Log.d("DB DEMO","grade record for " + studId + " for course " + courseId + " not found");
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Deletion error.." + ex.getMessage());
        }
    }
}