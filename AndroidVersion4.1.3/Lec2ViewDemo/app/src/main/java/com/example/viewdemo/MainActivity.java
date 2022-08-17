package com.example.viewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
    TextView txtViewWelcomeMsg; //you cannot call findViewById() - why is that? because setContentView() needs to be called before findViewById can be used
    boolean bigger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.d("GESTURE", "Started gesture demo...");

        ImageView imgViewSample = findViewById(R.id.imgViewSample);
        txtViewWelcomeMsg = findViewById(R.id.txtViewWelcomeMsg);


        Drawable img = getResources().getDrawable(R.drawable.border);
       // img.setBounds(0, 0, 100, 100);
        img.setBounds(0, 0, 0 + img.getIntrinsicWidth(), 0 +img.getIntrinsicHeight());
        txtViewWelcomeMsg.setCompoundDrawables(img,null,img,null); //setting the drawable image for textview to work as a border
        txtViewWelcomeMsg.setCompoundDrawablePadding(8);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            txtViewWelcomeMsg.setAlpha(1.0f); //alpha - transparency of the view, alpha value is 1.0 float value for fully opaque and 0 for fully transparent
        }

        Button btnShowTextOrImage = findViewById(R.id.btnShowTextOrImage);

        Button btnShowBoth = findViewById(R.id.btnShowBoth);

        btnShowTextOrImage.setOnClickListener((View view) -> {
            if (btnShowTextOrImage.getText().equals("Show Text")){
                imgViewSample.setVisibility(View.GONE);
                txtViewWelcomeMsg.setVisibility(View.VISIBLE);
                btnShowTextOrImage.setText("Show Image");
            } else {
                imgViewSample.setVisibility(View.VISIBLE);
                //txtViewWelcomeMsg.setVisibility(View.INVISIBLE); //try both INVISIBLE and GONE and see the difference
                txtViewWelcomeMsg.setVisibility(View.GONE);
                btnShowTextOrImage.setText("Show Text");
            }
        });

        btnShowBoth.setOnClickListener((View view) -> {
            imgViewSample.setVisibility(View.VISIBLE);
            txtViewWelcomeMsg.setVisibility(View.VISIBLE);
        });

        txtViewWelcomeMsg.setOnTouchListener(new CustomTouchListener(this){

            @Override
            public void onSwipeUp() {
                Toast.makeText(context,"Swipe up ",Toast.LENGTH_SHORT).show();
                //txtViewWelcomeMsg.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
                int horzGravity = txtViewWelcomeMsg.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
                txtViewWelcomeMsg.setGravity(horzGravity|Gravity.TOP);//bitwise or operator combine
                super.onSwipeUp();
            }

            @Override
            public void onSwipeDown() {
                Toast.makeText(context,"Swipe down ",Toast.LENGTH_SHORT).show();
                int horzGravity = txtViewWelcomeMsg.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
                txtViewWelcomeMsg.setGravity(horzGravity|Gravity.BOTTOM);//bitwise or operator combine
                super.onSwipeDown();
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(context,"Swipe left ",Toast.LENGTH_SHORT).show();
                int verGravity = txtViewWelcomeMsg.getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
                txtViewWelcomeMsg.setGravity(verGravity|Gravity.LEFT);//bitwise or operator combine
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                Toast.makeText(context,"Swipe right ",Toast.LENGTH_SHORT).show();
                //txtViewWelcomeMsg.setGravity(Gravity.TOP|Gravity.LEFT);
                int verGravity = txtViewWelcomeMsg.getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
                txtViewWelcomeMsg.setGravity(verGravity|Gravity.RIGHT);//bitwise or operator combine
                super.onSwipeRight();
            }

            @Override
            public void onDoubleClick() {
                super.onDoubleClick();

                Log.d("GESTURE", "Detected double click on the TextView");
                //whatever fucntionallyy you want for the long click of the textView.
                Toast.makeText(context,"Single Click on the text view detected", Toast.LENGTH_LONG).show();

                if(!bigger){
                    txtViewWelcomeMsg.setTextSize(txtViewWelcomeMsg.getTextSize()/getResources().getDisplayMetrics().density + 10);
                    bigger = true;
                }
                else {
                    txtViewWelcomeMsg.setTextSize(txtViewWelcomeMsg.getTextSize()/getResources().getDisplayMetrics().density - 10);
                    bigger = false;
                }
            }

            @Override
            public void onSingleClick() {
                super.onSingleClick();
                Log.d("GESTURE", "Detected single click on the TextView");
                //whatever fucntionallyy you want for the long click of the textView.
                Toast.makeText(context,"Single Click on the text view detected", Toast.LENGTH_LONG).show();

                if(txtViewWelcomeMsg.getCurrentTextColor()!= getResources().getColor(R.color.teal_200)){
                    txtViewWelcomeMsg.setTextColor(getResources().getColor(R.color.teal_200));
                }
                else {
                    txtViewWelcomeMsg.setTextColor(Color.WHITE);
                    //txtViewWelcomeMsg.setTextColor(Color.rgb(255,25,255));
                }
            }

            @Override
            public void onLongClick() {
                super.onLongClick();
                Log.d("GESTURE", "Detected long click on the TextView");
                //whatever fucntionallyy you want for the long click of the textView.
                Toast.makeText(context,"Long Click on the text view detected", Toast.LENGTH_LONG).show();
                if((txtViewWelcomeMsg.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG){
                    //this means textView currently has strike through
                    txtViewWelcomeMsg.setPaintFlags(txtViewWelcomeMsg.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    //takes the compl
                }
                else {
                    //currently textView does not have strike through
                    txtViewWelcomeMsg.setPaintFlags(txtViewWelcomeMsg.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("GESTURE", "detected onTocuh on the TextView");
                return super.onTouch(view, motionEvent);
            }
        });

        imgViewSample.setOnTouchListener(new CustomTouchListener(this){
            @Override
            public void onDoubleClick() {
                super.onDoubleClick();
                //Here, we re going to change scale type
                //FIT_XY - stretch image along X and Y of imageView
                //FIT_CENTER
                if(imgViewSample.getScaleType()!=ImageView.ScaleType.FIT_XY){
                    imgViewSample.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                else {
                    imgViewSample.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }

            @Override
            public void onSingleClick() {
                super.onSingleClick();
                if(imgViewSample.getDrawable().getConstantState()!= getResources().getDrawable(R.drawable.bird).getConstantState()){
                    imgViewSample.setImageResource(R.drawable.bird);
                }
                else {
                    imgViewSample.setImageResource(R.drawable.fire);
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