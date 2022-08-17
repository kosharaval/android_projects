package com.example.koshar_3375mt;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> taskList;
    Context context;

    Integer DoneVsNotDoneIndex = -1;
    Integer StaredIndex = -1;

    Integer TaskDoneVsDoneCount = 0;
    Integer TaskStaredCount = 0;

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.external_layout,parent,false);
        TaskViewHolder viewHolder = new TaskViewHolder(itemView);
        viewHolder.imageViewDoneVsNotDone = itemView.findViewById(R.id.imgViewDoneVsNotDone);
        viewHolder.imageViewStar = itemView.findViewById(R.id.imgViewStar);
        viewHolder.textViewTaskName = itemView.findViewById(R.id.txtViewDescription);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  TaskViewHolder holder, int position) {

        holder.textViewTaskName.setText(taskList.get(position).getTaskName());
        //holder.imageViewDoneVsNotDone.setImageResource(R.drawable.notdone);
        holder.imageViewStar.setImageResource(R.drawable.star);
        //holder.imageViewStar.setAlpha(0.25f);

        if(DoneVsNotDoneIndex == position){
            holder.imageViewDoneVsNotDone.setImageResource(R.drawable.done);
            holder.textViewTaskName.setPaintFlags(holder.textViewTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.imageViewDoneVsNotDone.setImageResource(R.drawable.notdone);
            holder.textViewTaskName.setPaintFlags(holder.textViewTaskName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if(StaredIndex == position){
            holder.imageViewStar.setAlpha(0.75f);
        }
        else {
            holder.imageViewStar.setAlpha(0.25f);
        }

        holder.imageViewDoneVsNotDone.setOnClickListener((view)->{
            if(DoneVsNotDoneIndex == position){
                //holder.imageViewDoneVsNotDone.setImageResource(R.drawable.done);
                DoneVsNotDoneIndex = -1;
                notifyDataSetChanged();
            }
            else {
                //holder.imageViewDoneVsNotDone.setImageResource(R.drawable.notdone);
                DoneVsNotDoneIndex = position;
                notifyDataSetChanged();
            }
        });

        holder.imageViewStar.setOnClickListener((view)->{
            if(StaredIndex == position){
                //holder.imageViewStar.setAlpha(0.75f);
                StaredIndex = -1;
                notifyDataSetChanged();
            }
            else {
                //holder.imageViewStar.setAlpha(0.25f);

                StaredIndex = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        //return 0;
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewDoneVsNotDone;
        ImageView imageViewStar;
        TextView textViewTaskName;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
