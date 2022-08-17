package com.example.lab06_300324116;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button btnCows, btnPigs;
    MediaPlayer mpCows, mpPigs;
    int playing;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCows = (Button) findViewById(R.id.btnCows);
        btnPigs = (Button) findViewById(R.id.btnPigs);

        btnCows.setOnClickListener(bCows);
        btnPigs.setOnClickListener(bPigs);

        mpCows = MediaPlayer.create(this,R.raw.cows);
        mpPigs = MediaPlayer.create(this,R.raw.pigs);

        playing = 0;

    }

    View.OnClickListener bCows = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (playing)
            {
                case 0:
                    mpCows.start();
                    playing = 1;
                    btnCows.setText("Pause Listening to cows");
                    btnPigs.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    mpCows.pause();
                    playing = 0;
                    btnCows.setText("Listening to Cows");
                    btnPigs.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    View.OnClickListener bPigs = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (playing)
            {
                case 0:
                    mpPigs.start();
                    playing = 1;
                    btnPigs.setText("Pause Listening to Piga");
                    btnCows.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    mpPigs.pause();
                    playing = 0;
                    btnPigs.setText("Listen to Pigs");
                    btnCows.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
}