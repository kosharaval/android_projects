package com.example.koshar_3375mt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> ListOfTaskNames = new ArrayList<>(Arrays.asList("Watch Movie","Do Laundry","Prepare for 3375 Midterm"));
    List<Task> TaskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageViewInfo = findViewById(R.id.imgViewInfo);
        TextView textViewStatus = findViewById(R.id.txtViewStatus);

        AddData();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTaskList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        TaskAdapter taskAdapter = new TaskAdapter(TaskList, this);
        recyclerView.setAdapter(taskAdapter);

        imageViewInfo.setOnTouchListener(new CustomTouchListener(this){

            @Override
            public void onDoubleClick() {
                textViewStatus.setText("Image View was Double Clicked");
                super.onDoubleClick();
            }

            @Override
            public void onSingleClick() {
                super.onSingleClick();
                textViewStatus.setText("Image View was Single Clicked");

            }

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                return super.onTouch(v, motionEvent);
            }
        });

    }

    private void AddData() {
        for(int i=0; i<ListOfTaskNames.size(); i++){
            Task newTask = new Task(ListOfTaskNames.get(i),0);
            TaskList.add(newTask);
        }
    }
}