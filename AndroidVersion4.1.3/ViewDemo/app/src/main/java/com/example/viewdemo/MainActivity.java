package com.example.viewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtWelcomeMsg;
    ImageView imgDemo;
    Button btnToggle, btnShow;

    boolean bigger = false;

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

        txtWelcomeMsg.setOnTouchListener( new CustomTouchListener(this){

            @Override
            public void onSwipeUp() {
                Toast.makeText(context,"Swipe up ",Toast.LENGTH_SHORT).show();
                //txtViewWelcomeMsg.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
                int horzGravity = txtWelcomeMsg.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
                txtWelcomeMsg.setGravity(horzGravity|Gravity.TOP);//bitwise or operator combine
                super.onSwipeUp();
            }

            @Override
            public void onSwipeDown() {
                Toast.makeText(context,"Swipe down ",Toast.LENGTH_SHORT).show();
                int horzGravity = txtWelcomeMsg.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
                txtWelcomeMsg.setGravity(horzGravity|Gravity.BOTTOM);//bitwise or operator combine
                super.onSwipeDown();
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(context,"Swipe left ",Toast.LENGTH_SHORT).show();
                int verGravity = txtWelcomeMsg.getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
                txtWelcomeMsg.setGravity(verGravity|Gravity.LEFT);//bitwise or operator combine
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                Toast.makeText(context,"Swipe right ",Toast.LENGTH_SHORT).show();
                //txtViewWelcomeMsg.setGravity(Gravity.TOP|Gravity.LEFT);
                int verGravity = txtWelcomeMsg.getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
                txtWelcomeMsg.setGravity(verGravity|Gravity.RIGHT);//bitwise or operator combine
                super.onSwipeRight();
            }

            @Override
            public void onDoubleClick() {
                super.onDoubleClick();

                Log.d("GESTURE", "Detected double click on the TextView");
                //whatever fucntionallyy you want for the long click of the textView.
                Toast.makeText(context,"Single Click on the text view detected", Toast.LENGTH_LONG).show();

                if(!bigger){
                    txtWelcomeMsg.setTextSize(txtWelcomeMsg.getTextSize()/getResources().getDisplayMetrics().density + 10);
                    bigger = true;
                }
                else {
                    txtWelcomeMsg.setTextSize(txtWelcomeMsg.getTextSize()/getResources().getDisplayMetrics().density - 10);
                    bigger = false;
                }
            }

            @Override
            public void onLongClick() {
                super.onLongClick();
                Log.d("Main Activity", "Detected long click on the txtWelcomeMsg");
                Toast.makeText(context,"Long Click on the text view detected", Toast.LENGTH_LONG).show();

                if((txtWelcomeMsg.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG){
                    //this means textView currently has strike through
                    txtWelcomeMsg.setPaintFlags(txtWelcomeMsg.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    Toast.makeText(context,"Strick through removed", Toast.LENGTH_LONG).show();
                    //takes the compl
                }
                else {
                    //currently textView does not have strike through
                    txtWelcomeMsg.setPaintFlags(txtWelcomeMsg.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Toast.makeText(context,"Strick through applied", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onSingleClick() {
                super.onSingleClick();
                Log.d("GESTURE", "Detected single click on the TextView");
                //whatever fucntionallyy you want for the long click of the textView.
                Toast.makeText(context,"Single Click on the text view detected", Toast.LENGTH_LONG).show();

                if(txtWelcomeMsg.getCurrentTextColor()!= ContextCompat.getColor(context,R.color.teal_200)){
                    txtWelcomeMsg.setTextColor(ContextCompat.getColor(context,R.color.teal_200));
                }
                else {
                    txtWelcomeMsg.setTextColor(Color.WHITE);
                    //txtViewWelcomeMsg.setTextColor(Color.rgb(255,25,255));
                }
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d("GESTURE", "Detected onTocuh on the txtWelcomeMsg");
                return super.onTouch(v, event);
            }
        });

        imgDemo.setOnTouchListener(new CustomTouchListener(this){
            @Override
            public void onDoubleClick() {
                super.onDoubleClick();
                //Here, we re going to change scale type
                //FIT_XY - stretch image along X and Y of imageView
                //FIT_CENTER
                if(imgDemo.getScaleType()!=ImageView.ScaleType.FIT_XY){
                    imgDemo.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                else {
                    imgDemo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }

            @Override
            public void onSingleClick() {
                super.onSingleClick();
                if(imgDemo.getDrawable().getConstantState()!= ContextCompat.getDrawable(context,R.drawable.bird).getConstantState()){
                    imgDemo.setImageResource(R.drawable.bird);
                }
                else {
                    imgDemo.setImageResource(R.drawable.fire);
                }
            }

            @Override
            public void onLongClick() {
                super.onLongClick();
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("GESTURE","Detected onTouch on imageView...");
                return super.onTouch(view, motionEvent);
            }
        });
    }
}