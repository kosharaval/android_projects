package com.example.viewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.Dimension;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtViewWelcomeMsg; //you cannot call findViewById() - why is that? because setContentView() needs to be called before findViewById can be used
    boolean bigger = false; //to maintain current font size state of the textview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.d("GESTUREDEM","Started gesture demo....");

        ImageView imgViewSample = findViewById(R.id.imgViewSample);
        txtViewWelcomeMsg = findViewById(R.id.txtViewWelcomeMsg);


        Drawable img = getResources().getDrawable(R.drawable.border);
       // img.setBounds(0, 0, 100, 100);
        img.setBounds(0, 0, 0 + img.getIntrinsicWidth(), 0 +img.getIntrinsicHeight());
        txtViewWelcomeMsg.setCompoundDrawables(img,null,img,null); //setting the drawable image for textview to work as a border
        txtViewWelcomeMsg.setCompoundDrawablePadding(8);

        txtViewWelcomeMsg.setAlpha(1.0f); //alpha - transparency of the view, alpha value is 1.0 float value for fully opaque and 0 for fully transparent

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
            public void onDoubleClick() {
                super.onDoubleClick();
                Log.d("GESTUREDEMO","Detected double click of TextView");

                if (!bigger){
                    txtViewWelcomeMsg.setTextSize(
                            txtViewWelcomeMsg.getTextSize()/getResources().getDisplayMetrics().density + 10);
                    bigger = true;
                } else{
                    txtViewWelcomeMsg.setTextSize(
                            txtViewWelcomeMsg.getTextSize()/getResources().getDisplayMetrics().density - 10);
                    bigger = false;
                }
            }

            @Override
            public void onSingleClick() {
                super.onSingleClick();
                Log.d("GESTUREDEMO","Detected single click on the TextView");

                if (txtViewWelcomeMsg.getCurrentTextColor()
                        != getResources().getColor(R.color.teal_200)){
                    txtViewWelcomeMsg.setTextColor(getResources().getColor(R.color.teal_200));
                } else {
                    txtViewWelcomeMsg.setTextColor(Color.rgb(255,255,255));
                }
            }

            @Override
            public void onLongClick() {
                super.onLongClick();
                Log.d("GESTUREDEMO","Detected long click on the TextView");
                //whatever functionality you want for the long click of the textview
                Toast.makeText(context, "Long Click on the text view detected", Toast.LENGTH_SHORT).show();
                if ((txtViewWelcomeMsg.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG)
                                == Paint.STRIKE_THRU_TEXT_FLAG){
                    //this means textview currently has strike through
                    txtViewWelcomeMsg.setPaintFlags(txtViewWelcomeMsg.getPaintFlags()
                            & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    //takes the complement of STRIKE_THROUGH which is 11110111
                    //and bitwise AND with current flag
                    //Copies all other flag bits as is
                    //except make STRIKETHROUGH 0 (no strike through)
                } else {
                    //currently textview does not have strike through
                    txtViewWelcomeMsg.setPaintFlags(txtViewWelcomeMsg.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
                    //this takes current flags and ORs it with strike through
                    //thus copying all the other flags as is and making strike through TRUE (1)
                }
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("GESTUREDEMO","Detected onTouch on the TextView...");
                return super.onTouch(view, motionEvent);
            }
        });

        imgViewSample.setOnTouchListener(new CustomTouchListener(this){
            @Override
            public void onDoubleClick() {
                super.onDoubleClick();
                //Here, we are going to change scale type
                //FIT_XY - stretch image along X and Y of ImageView
                //FIT_CENTER - means resize image to fit in ImageView while maintaining the aspect ratio
                if (imgViewSample.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imgViewSample.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    imgViewSample.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }

            @Override
            public void onSingleClick() {
                super.onSingleClick();
                //On single click, change image back and forth to bird and fire
                if (imgViewSample.getDrawable().getConstantState()
                        != getResources().getDrawable(R.drawable.bird).getConstantState()) {
                    imgViewSample.setImageResource(R.drawable.bird);
                } else {
                    imgViewSample.setImageResource(R.drawable.fire);
                }
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("GESTUREDEMO","Detected onTouch on ImageView...");
                return super.onTouch(view, motionEvent);
               // return false; //useful to test if set to false, other gestures may not be detected
            }
        });

    }
}