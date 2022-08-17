package com.example.reza.broadcastreceiversdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Reza on 2018-03-23.
 */

public class MySecondReceiver extends BroadcastReceiver{

    private static final String TAG = MySecondReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "Hello from 2nd Receiver");
        Toast.makeText(context, "Hello from 2nd Receiver", Toast.LENGTH_LONG).show();

    }
}
