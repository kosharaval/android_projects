package com.example.firebasetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText etStudentName,etStudentMajor,etStudentID;
    private Button buttonAddStudent;
    DatabaseReference databaseReference;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"Database Connection is Successful",Toast.LENGTH_LONG).show();

        etStudentName = findViewById(R.id.etStudentName);
        etStudentMajor = findViewById(R.id.etStudentMajor);
        etStudentID = findViewById(R.id.etStudentID);
        buttonAddStudent = findViewById(R.id.buttonAddStudent);

        student = new Student();
        //Firebase Database object
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student");

        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentName = etStudentName.getText().toString().trim();
                String studentMajor = etStudentMajor.getText().toString().trim();
                String studentId = etStudentID.getText().toString().trim();
                student.setStudentName(studentName);
                student.setStudentMajor(studentMajor);
                student.setStudentID(studentId);

                databaseReference.push().setValue(student);
                Toast.makeText(MainActivity.this,"New Student Added",Toast.LENGTH_LONG).show();
            }
        });
    }
}