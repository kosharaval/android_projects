package com.example.koshar_3375mt;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class CustomTouchListener implements View.OnTouchListener {

    Context context;
    GestureDetectorCompat gestureDetectorCompat;

    public CustomTouchListener(Context context){
        this.context = context;
        //need to create a new GestureDetectorCompat object and set it to gestureDetectorCompat
        //this needs two things - one is context, the other CustomOnGestureListener()

        gestureDetectorCompat = new GestureDetectorCompat(context, new CustomOnGestureListener());
    }

    public class CustomOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        public CustomOnGestureListener() { }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleClick();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onSingleClick();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public void onDoubleClick() {
    }

    public void onSingleClick(){

    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        //return false;
        return gestureDetectorCompat.onTouchEvent(motionEvent);
    }
}
