package com.example.reza.broadcastreceiversdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendBroadcastMessage(View view) {

       Intent intent = new Intent(this, MyFirstReceiver.class);
     //  Intent intent = new Intent(".my.custom.action.name");

        intent.putExtra("name", "Reza");
        intent.putExtra("courses", 4);

        sendBroadcast(intent);

        Toast.makeText(this, "After Sending the Broadcast", Toast.LENGTH_LONG).show();
    }

    public void broadcastToInnerReceiver(View view) {

       Intent intent = new Intent (this, myThirdReceiverInner.class);
     // Intent intent = new Intent(".my.custom.anotheraction.name");

      Bundle bundle = new Bundle();
      bundle.putString("name", "Reza");
      bundle.putInt("courses", 4);
      intent.putExtras(bundle);

      sendBroadcast(intent);
    }

    public static class myThirdReceiverInner extends BroadcastReceiver {

          private static final String TAG = myThirdReceiverInner.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {

            String name = intent.getStringExtra("name");
            int courses = intent.getIntExtra("courses", 0);
            Log.i(TAG, "Name: " + name + ", Courses: " + courses);


            Log.i(TAG, "Hello from 3rd Receiver");
            Toast.makeText(context, "Hello from 3rd Receiver", Toast.LENGTH_LONG).show();


        }
    }
}
