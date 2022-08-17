package com.example.finalexam_kosharaval_300324116;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FirstFragment.FirstFragmentListener{

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFirst,firstFragment)
                .replace(R.id.fragmentSecond,secondFragment)
                .commit();
    }

    @Override
    public void onSeekBarChange(int inputTextSize, String inputString) {
        secondFragment.updateTextSize(inputTextSize,inputString);
    }
}