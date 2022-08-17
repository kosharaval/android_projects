package com.example.sensortutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void translation(float tx, float ty, float tz) {
                if(tx>1.0f){
                    textView.setText("TX Value: " + tx +
                            "\nTY Value: " + ty +
                            "\nTZ Value: " + tz);
                    textView.setTextColor(Color.BLUE);
                }
                else if(ty<-1.0f)
                {
                    textView.setText("TX Value: " + tx +
                            "\nTY Value: " + ty +
                            "\nTZ Value: " + tz);
                    textView.setTextColor(Color.RED);
                }
            }
        });

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void translation(float rx, float ry, float rz) {
                if(rx>1.0f){
                    textView.setText("TX Value: " + rx +
                            "\nTY Value: " + ry +
                            "\nTZ Value: " + rz);
                    textView.setTextColor(Color.DKGRAY);
                }
                else if(ry<-1.0f)
                {
                    textView.setText("TX Value: " + rx +
                            "\nTY Value: " + ry +
                            "\nTZ Value: " + rz);
                    textView.setTextColor(Color.GREEN);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        accelerometer.register();
        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unregister();
        gyroscope.unregister();
    }
}