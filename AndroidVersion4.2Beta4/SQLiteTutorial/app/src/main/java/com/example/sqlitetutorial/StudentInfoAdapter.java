package com.example.sqlitetutorial;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.StudentInfoViewHolder>
{
    private ArrayList<Student> studentArrayList;
    public static class StudentInfoViewHolder extends RecyclerView.ViewHolder{

        public TextView txvSName;
        public TextView txvSMajor;
        public TextView txvSID;

        public StudentInfoViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txvSName = itemView.findViewById(R.id.txvSName);
            txvSMajor = itemView.findViewById(R.id.txvSMajor);
            txvSID = itemView.findViewById(R.id.txvSID);
        }
    }
    public StudentInfoAdapter(ArrayList<Student> students)
    {
        studentArrayList = students;
    }
    @NonNull
    @Override
    public StudentInfoAdapter.StudentInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclerview,parent,false);
        StudentInfoAdapter.StudentInfoViewHolder evh = new StudentInfoAdapter.StudentInfoViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull StudentInfoAdapter.StudentInfoViewHolder holder, int position) {
        Student currentItem = studentArrayList.get(position);

        holder.txvSName.setText(currentItem.getsName());
        holder.txvSMajor.setText(currentItem.getsMajor());
        holder.txvSID.setText(currentItem.getsID());
      }
    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }
}