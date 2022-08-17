package com.example.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<Student> studentArrayList = new ArrayList<>();
    RecyclerView studentRecyclerView;
    StudentInfoAdapter studentInfoAdapter;

    EditText etSName,etSMajor,etSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSName = findViewById(R.id.etName);
        etSMajor = findViewById(R.id.etMajor);
        etSID = findViewById(R.id.etId);

        studentRecyclerView = findViewById(R.id.studentTableRecyclerView);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentInfoAdapter = new StudentInfoAdapter(studentArrayList);
        studentRecyclerView.setAdapter(studentInfoAdapter);
    }

    public void saveData(View view) {
        String sName = etSName.getText().toString();
        String sMajor = etSMajor.getText().toString();
        String sID = etSID.getText().toString();
        db.insertStudent(sName,sMajor,sID);
        studentInfoAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Data inserted",Toast.LENGTH_SHORT).show();
    }

    public void loadData(View view) {

        List<Student> studentList = null;
        studentList = db.getAllStudent();
        studentArrayList.clear();
        for (Student s : studentList){
            studentArrayList.add(new Student(s.getsName(),s.getsMajor(),s.getsID()));
        }
        if(!studentArrayList.isEmpty()){
            studentInfoAdapter.notifyDataSetChanged();
        }
    }
}