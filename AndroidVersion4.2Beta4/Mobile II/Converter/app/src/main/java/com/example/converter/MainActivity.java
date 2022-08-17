package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Convert(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextNumber);
        TextView textView = (TextView) findViewById(R.id.textView);

        String dollar = editText.getText().toString();

        if(!dollar.equals("")){
            Float euro = Float.valueOf(dollar) * 0.85f;
            textView.setText(euro.toString());
        }
        else {
            textView.setText("You didn't enter any value!");
        }
    }
}