package com.example.animationtutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewBattery;
    Button buttonPlayAnimation;
    public AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewBattery = findViewById(R.id.imageViewBattery);
        buttonPlayAnimation = findViewById(R.id.buttonPlayAnimation);

        imageViewBattery.setBackgroundResource(R.drawable.animation);
        animationDrawable = (AnimationDrawable) imageViewBattery.getDrawable();

//        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.lefttoright);
//        buttonPlayAnimation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageViewBattery.startAnimation(animation);
//            }
//        });
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        animationDrawable.start();
//    }
}