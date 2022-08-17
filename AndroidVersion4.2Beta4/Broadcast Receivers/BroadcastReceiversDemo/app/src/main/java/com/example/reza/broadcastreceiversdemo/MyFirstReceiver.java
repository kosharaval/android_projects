package com.example.reza.broadcastreceiversdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Reza on 2018-03-23.
 */

public class MyFirstReceiver extends BroadcastReceiver{

    private static final String TAG = MyFirstReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {

        String name = intent.getStringExtra("name");
        int courses = intent.getIntExtra("courses", 0);
        Log.i(TAG, "Name: " + name + ", Courses: " + courses);


        Log.i(TAG, "Hello from 1st Receiver, Thread Name: " + Thread.currentThread().getName());
        Toast.makeText(context, "Hello from 1st Receiver", Toast.LENGTH_LONG).show();
/*
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
    }
}
