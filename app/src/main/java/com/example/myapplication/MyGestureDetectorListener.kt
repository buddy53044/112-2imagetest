package com.example.myapplication

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class MyGestureDetectorListener: GestureDetector.SimpleOnGestureListener() {
    override fun onDown(e: MotionEvent): Boolean {
        Log.d("GestureDetectorListener", "onDown");

        return super.onDown(e)
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d("GestureDetectorListener", "onSingleTapUp");

        return super.onSingleTapUp(e)
    }
}
