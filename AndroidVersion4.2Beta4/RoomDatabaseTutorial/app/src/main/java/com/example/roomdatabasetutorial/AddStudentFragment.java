package com.example.roomdatabasetutorial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudentFragment extends Fragment implements View.OnClickListener{

    private EditText etStudentName,etStudentMajor,etStudentID;
    private Button buttonAddStudent;

    public AddStudentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        etStudentName = view.findViewById(R.id.etStudentName);
        etStudentMajor = view.findViewById(R.id.etStudentMajor);
        etStudentID = view.findViewById(R.id.etStudentID);
        buttonAddStudent = view.findViewById(R.id.buttonAddStudent);
        buttonAddStudent.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        String studentName = etStudentName.getText().toString();
        String studentMajor = etStudentMajor.getText().toString();
        String studentId = etStudentID.getText().toString();
        if(studentId.equals("")){
            Toast.makeText(getActivity(),"Student ID is mandatory",Toast.LENGTH_SHORT).show();
        }
        else {
            Student student = new Student();
            student.setStudentName(studentName);
            student.setStudentMajor(studentMajor);
            student.setStudentID(studentId);
            MainActivity.studentDatabase.studentDAO().addStudent(student);
            Toast.makeText(getActivity(),"Student Info Added",Toast.LENGTH_SHORT).show();
            etStudentName.setText("");
            etStudentMajor.setText("");
            etStudentID.setText("");
        }

    }
}