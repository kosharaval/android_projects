package com.example.viewdemo;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class CustomTouchListener implements View.OnTouchListener{

    Context context;
    GestureDetectorCompat gestureDetectorCompat;

    public CustomTouchListener(Context context) {
        this.context = context;
        gestureDetectorCompat = new GestureDetectorCompat(context,new CustomOnGestureListener());
    }

    public CustomTouchListener() {

    }

    public class CustomOnGestureListener extends GestureDetector.SimpleOnGestureListener{

        public CustomOnGestureListener() {
            Log.d("GESTURE", "Started gesture demo...");
        }

        private static final int SWIPE_DIST_THRESHOLD = 10;
        private static final int SWIPE_VEL_THRESHOLD = 75;

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            Log.d("Gesture Demo","Long press detected inside CustomOnGestureListener");
            //Need a method to execute when LongPress is detected.
            onLongClick();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //return super.onFling(e1, e2, velocityX, velocityY);
            float distX = e2.getX() - e1.getX();
            float distY = e2.getY() - e1.getY();

            Log.d("SWIPE","Enterec on fling");
            Log.d("SWIPE","distX = " + distX+ " ,VelX = " + velocityX +
                    "distY" + distY + ", VelocityY= " + velocityY);

            if(Math.abs(distX) > Math.abs(distY) && Math.abs(distX) > SWIPE_DIST_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VEL_THRESHOLD){
                //this means its a horizontal swipe
                if(distX > 0){
                    onSwipeRight();
                }else {
                    onSwipeLeft();
                }
                return true;
            }else if(Math.abs(distX) < Math.abs(distY) && Math.abs(distY) >SWIPE_DIST_THRESHOLD
                    && Math.abs(velocityY)>SWIPE_VEL_THRESHOLD){
                if(distY>0){
                    onSwipeDown();
                }else {
                    onSwipeUp();
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            //return super.onDown(e);//if this retruns flase no other gestures will be checked.
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("GESTURE","Double tap detected .. will execute diuble click next");
            onDoubleClick();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            Log.d("GESTURE","Long click method inside Custom Touch Listener");
            onSingleClick();
            return super.onSingleTapConfirmed(e);
        }
    }

    public void onSwipeUp() {
        Log.d("SWIPE","Swipe UP detected in the custom touch listener class");
    }

    public void onSwipeDown() {
        Log.d("SWIPE","Swipe DOWN detected in the custom touch listener class");
    }

    public void onSwipeLeft() {
        Log.d("SWIPE","Swipe LEFT detected in the custom touch listener class");
    }

    public void onSwipeRight() {
        Log.d("SWIPE","Swipe RIGHT detected in the custom touch listener class");
    }

    public void onDoubleClick() {
        Log.d("GESTURE","Double Click Method detected .. will execute double click next");
    }

    public void onSingleClick() {

        Log.d("GESTURE","Long click method inside Custom Touch Listener");
    }

    public void onLongClick() {

        Log.d("GESTURE","Long click method inside Custom Touch Listener");
        //here you do not want to view specific changes, such as changing textView, imageView etc.
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return gestureDetectorCompat.onTouchEvent(event);
        //return false;
    }
}
