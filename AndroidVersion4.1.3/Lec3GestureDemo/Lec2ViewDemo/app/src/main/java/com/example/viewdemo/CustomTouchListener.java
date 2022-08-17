package com.example.viewdemo;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class CustomTouchListener implements View.OnTouchListener {
    Context context;
    GestureDetectorCompat gestureDetectorCompat; //declaring a gesture detector object to detect simple gestures

    public CustomTouchListener(Context context) {
        this.context = context;
        //need to create a new GestureDetectorCompat object and set it to gestureDetectorCompat
        //this needs two things - one is context, the other CustomOnGestureListener()
        gestureDetectorCompat = new GestureDetectorCompat(context,new CustomOnGestureListener());
    }

    public class CustomOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            Log.d("GESTUREDEMO","Long Press Detected inside CustomOnGestureListener...");
            //Need a method to execute when longPress is detected
            onLongClick();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            //return super.onDown(e); //if this returns false, no other gestures will be checked
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("GESTUREDEMO","Double tap detected..." +
                                        "will execute double click next...");
            onDoubleClick(); //is again added to the CustomTouchListener as a public method
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("GESTUREDEMO","Single Tap Confirmed");
            onSingleClick(); //this method stub needs to be created in the CustomTouchListener needs to be added here before the return
            return super.onSingleTapConfirmed(e);
        }
    }

    public void onDoubleClick() {
        Log.d("GESTUREDEMO","Entering double click method in custom touch listener...");
    }

    public void onSingleClick() {
        Log.d("GESTUREDEM","Single Click Method inside Custom Touch Listener");
    }

    public void onLongClick() {
        Log.d("GESTUREDEM","Long Click Method inside Custom Touch Listener");
        //here you do not want view specific changes, such as changing textview, imageview etc.
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //return false; //if this returns false, the execution flow stops here
        return gestureDetectorCompat.onTouchEvent(motionEvent);

    }
}
