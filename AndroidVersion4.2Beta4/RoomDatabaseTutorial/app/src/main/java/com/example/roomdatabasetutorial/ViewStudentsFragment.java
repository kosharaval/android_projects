package com.example.roomdatabasetutorial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ViewStudentsFragment extends Fragment {

    List<Student> studentList = MainActivity.studentDatabase.studentDAO().getStudents();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    public ViewStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_students, container, false);

        recyclerView = view.findViewById(R.id.studentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter(studentList);
        recyclerView.setAdapter(adapter);


        return view;
    }
}