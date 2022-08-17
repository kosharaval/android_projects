package com.example.viewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtWelcomeMsg;
    ImageView imgDemo;
    Button btnToggle, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWelcomeMsg = (TextView) findViewById(R.id.txtWelcomeMsg);
        imgDemo = (ImageView) findViewById(R.id.imgDemo);
        btnToggle = (Button) findViewById(R.id.btnToggle);
        btnShow = (Button) findViewById(R.id.btnShow);

        Drawable img = getResources().getDrawable(R.drawable.border,getTheme());
        img.setBounds(0,0,0+img.getIntrinsicWidth(),0+img.getIntrinsicHeight());
        txtWelcomeMsg.setCompoundDrawables(img,null,img,null);
        txtWelcomeMsg.setCompoundDrawablePadding(8);
        txtWelcomeMsg.setAlpha(1.0f);// transparency of the view, 1f value for fully opaque, 0 is for fully transparent

        btnToggle.setOnClickListener((View view) ->{
            if(btnToggle.getText().equals("Show Text"))
            {
                imgDemo.setVisibility(View.GONE);
                txtWelcomeMsg.setVisibility(View.VISIBLE);
                btnToggle.setText("Show Image");
            }
            else
            {
                imgDemo.setVisibility(View.VISIBLE);
                txtWelcomeMsg.setVisibility(View.INVISIBLE);
                btnToggle.setText("Show Text");
            }
        });

        btnShow.setOnClickListener((View view)->{
            imgDemo.setVisibility(View.VISIBLE);
            txtWelcomeMsg.setVisibility(View.VISIBLE);
        });
    }
}