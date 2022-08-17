package com.example.burgercaloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int BEEF = 120;
    int LAMB = 190;
    int OSTRICH = 170;
    int ASIAGO = 100;
    int CREME = 130;
    int PROSCIUTTO = 125;

    int caloriCounter = 0;
    int pattyINT,cheeseINT,teaspoonInt;
    int initalProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewOutput= (TextView) findViewById(R.id.textViewOutput);
        SeekBar seekBarCaviarSauce = findViewById(R.id.seekBarCaviarSauce);

        CheckBox checkBoxProsciutto = findViewById(R.id.checkBoxProsciutto);

        seekBarCaviarSauce.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                teaspoonInt = teaspoonInt + (progress - initalProgress);
                initalProgress = progress;
                if(checkBoxProsciutto.isChecked()){
                    caloriCounter = pattyINT + cheeseINT + teaspoonInt + PROSCIUTTO;
                }
                else {
                    caloriCounter = pattyINT + cheeseINT + teaspoonInt;
                }
                textViewOutput.setText("Calories: " + String.valueOf(caloriCounter));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //textViewOutput.setText("Calories: " + String.valueOf(caloriCounter));
    }

    public void onRadioGroupPattyClicked(View v)
    {
        RadioButton radioButtonBeef =  findViewById(R.id.radioButtonBeefPatty);
        RadioButton radioButtonLamb =  findViewById(R.id.radioButtonLambPatty);
        RadioButton radioButtonOstrich =  findViewById(R.id.radioButtonOstrichPatty);

        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()){
            case R.id.radioButtonBeefPatty:
                pattyINT =  BEEF;
                break;
            case R.id.radioButtonLambPatty:
                pattyINT = LAMB;
                break;
            case R.id.radioButtonOstrichPatty:
                pattyINT = OSTRICH;
        }
    }
    public void onRadioGroupCheeseClicked(View v){

        RadioButton radioButtonAsisago =  findViewById(R.id.radioButtonAsiagoCheese);
        RadioButton radioButtonCreme =  findViewById(R.id.radioButtonCremeFriche);

        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()){
            case R.id.radioButtonAsiagoCheese:
                cheeseINT = ASIAGO;
                break;
            case R.id.radioButtonCremeFriche:
                cheeseINT = CREME;
        }
    }

}