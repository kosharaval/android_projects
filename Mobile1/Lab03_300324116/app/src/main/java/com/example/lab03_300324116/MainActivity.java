package com.example.lab03_300324116;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    double perGallon = 250;
    double wallHeight,wallLength, roomArea, paintNeeded;
    String groupColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText height = (EditText) findViewById(R.id.txtHeight);
        final EditText length = (EditText) findViewById(R.id.txtLength);
        final Spinner color = (Spinner) findViewById(R.id.txtColor);
        final TextView paint = (TextView) findViewById(R.id.txtPaint);
        Button gallons = (Button) findViewById(R.id.btnCal);

        gallons.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        wallHeight = Double.parseDouble(height.getText().toString());
                        wallLength = Double.parseDouble(length.getText().toString());
                        roomArea = wallHeight * wallLength;
                        paintNeeded = roomArea / perGallon;
                        DecimalFormat perSquareFeet = new DecimalFormat("###.##");
                        groupColor = color.getSelectedItem().toString();

                        paint.setText("Paint selected: " + groupColor + ". Total paint required " + perSquareFeet.format(paintNeeded));
                    }
                }
        );
    }
}