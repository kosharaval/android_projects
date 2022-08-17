package com.example.lec7gridviewdem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //we want to set up a timer task, and a timer
        //timer task says what task should be done
        //timer task implements runnable (to create a thread for performing a task

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //start the next task
                startActivity(new Intent(SplashActivity.this,
                                        MainActivity.class));
                //finish the current activity object
                finish();
            }
        };

        //time specifies the delay and the task to execute after the delay (ms)
        Timer timer = new Timer();

        //schedule the timer
        timer.schedule(timerTask,3000); //delay is in milliseconds
    }
}