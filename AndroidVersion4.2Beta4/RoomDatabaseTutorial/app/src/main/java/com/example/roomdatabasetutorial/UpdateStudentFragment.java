package com.example.roomdatabasetutorial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateStudentFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner spinnerStudent;
    private EditText etStudentName, etStudentMajor;
    private Button buttonUpdateStudent;

    public UpdateStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_student, container, false);

        spinnerStudent = view.findViewById(R.id.spinnerStudent);
        etStudentName = view.findViewById(R.id.etStudentName);
        etStudentMajor = view.findViewById(R.id.etStudentMajor);
        buttonUpdateStudent = view.findViewById(R.id.buttonUpdateStudent);

        bindSpinner();

        spinnerStudent.setOnItemSelectedListener(this);
        buttonUpdateStudent.setOnClickListener(this);

        return view;
    }

    public void bindSpinner(){
        List<Student> studentList = MainActivity.studentDatabase.studentDAO().getStudents();
        ArrayList<String> studentIDList = new ArrayList<>();
        studentIDList.add("Select Student ID");
        for (Student s : studentList){
            studentIDList.add(s.getStudentID());
        }
        ArrayAdapter<String> studentArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,studentIDList);
        studentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudent.setAdapter(studentArrayAdapter);
    }

    @Override
    public void onClick(View v) {

        String studentId = String.valueOf(spinnerStudent.getSelectedItem());
        String studentName = etStudentName.getText().toString();
        String studentMajor = etStudentMajor.getText().toString();
        Student student = new Student();
        student.setStudentName(studentName);
        student.setStudentMajor(studentMajor);
        student.setStudentID(studentId);

        MainActivity.studentDatabase.studentDAO().updateStudent(student);
        Toast.makeText(getActivity(),"Student Info Updated",Toast.LENGTH_SHORT).show();

        etStudentName.setText("");
        etStudentMajor.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String studentId = String.valueOf(spinnerStudent.getSelectedItem());
        List<Student> studentInfo = MainActivity.studentDatabase.studentDAO().getStudentWithId(studentId);
        for (Student s : studentInfo){
            etStudentName.setText(s.getStudentName());
            etStudentMajor.setText(s.getStudentMajor());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}