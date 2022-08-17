package com.example.roomdatabasetutorial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteStudentFragment extends Fragment implements View.OnClickListener{

    private Button buttonDeleteStudent;
    private Spinner spinnerStudent;
    public DeleteStudentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_student, container, false);
        spinnerStudent = view.findViewById(R.id.spinnerStudent);
        buttonDeleteStudent = view.findViewById(R.id.buttonDeleteStudent);
        bindSpinner();
        buttonDeleteStudent.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        String studentId = spinnerStudent.getSelectedItem().toString();
        Student student = new Student();
        student.setStudentID(studentId);

        MainActivity.studentDatabase.studentDAO().deleteStudent(student);
        Toast.makeText(getActivity(),"Student data deleted.",Toast.LENGTH_SHORT).show();
        bindSpinner();
        //spinnerStudent.notify();
    }
    public void bindSpinner(){
        List<Student> studentList = MainActivity.studentDatabase.studentDAO().getStudents();
        ArrayList<String> studentIDList = new ArrayList<>();
        for (Student s : studentList){
            studentIDList.add(s.getStudentID());
        }
        ArrayAdapter<String> studentArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,studentIDList);
        studentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudent.setAdapter(studentArrayAdapter);

    }
}