package com.example.lec8dbdemo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    List<String[]> studentData; //Here String[] is a 3 element array that stores Id, Name and Dept

    public StudentAdapter(List<String[]> studentData) {
        this.studentData = studentData;
    }

    @Override
    public int getCount() {
        return studentData.size();
    }

    //Here, instead of Object you can return String[]
    //which will then return ith item from list of String[]
    @Override
    public Object getItem(int i) {
        return null;
    }

    //Return i OR even better you can
    //return String[0] of the ith item in the list
    //String[0] corresponds to the Student Id (parse the Id String before returning
    //long
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_studentrec, viewGroup, false);
        }
        //populate the view
        TextView txtViewStudId = view.findViewById(R.id.txtViewStudId);
        TextView txtViewStudName = view.findViewById(R.id.txtViewStudName);
        TextView txtViewStudDept = view.findViewById(R.id.txtViewStudDept);

        //set the text for each item in the list view
        txtViewStudId.setText(studentData.get(i)[0]); //0th element in the string array of the ith item in the list
        txtViewStudName.setText(studentData.get(i)[1]);
        txtViewStudDept.setText(studentData.get(i)[2]);

        if (i == 0){
            txtViewStudId.setTextColor(Color.RED);
            txtViewStudName.setTextColor(Color.RED);
            txtViewStudDept.setTextColor(Color.RED);

        }
        //I can have other logic for different look and
        //feel based on the data or index i
        return view;
    }

}
