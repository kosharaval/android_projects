package com.example.roomdatabasetutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Student> studentList;
    public RecyclerAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_studentinfo, viewGroup,false);
        RecyclerAdapter.ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull  RecyclerAdapter.ViewHolder holder, int position) {

        Student currentItem = studentList.get(position);
        holder.txvStudentName.setText("Name: " + currentItem.getStudentName());
        holder.txvStudentMajor.setText("Major: " + currentItem.getStudentMajor());
        holder.txvStudentId.setText("ID: " + currentItem.getStudentID());
    }

    @Override
    public int getItemCount() { return studentList.size(); }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txvStudentName,txvStudentMajor,txvStudentId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txvStudentName = itemView.findViewById(R.id.txvStudentName);
            txvStudentMajor = itemView.findViewById(R.id.txvStudentMajor);
            txvStudentId = itemView.findViewById(R.id.txvStudentId);
        }
    }
}
