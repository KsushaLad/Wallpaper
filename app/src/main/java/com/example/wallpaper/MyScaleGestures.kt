package com.example.wallpaper

import android.content.Context
import android.view.ScaleGestureDetector

import android.view.MotionEvent
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View


class MyScaleGestures(c: Context?) : View.OnTouchListener, OnScaleGestureListener {
    private var view: View? = null
    private val gestureScale: ScaleGestureDetector
    private var scaleFactor = 0.1f
    private var inScale = false
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        this.view = view
        gestureScale.onTouchEvent(event)
        return true
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleFactor *= detector.scaleFactor
        scaleFactor =
            if (scaleFactor < 1) 1F else scaleFactor // prevent our view from becoming too small //
        scaleFactor = (scaleFactor * 1150).toInt()
            .toFloat() / 1150 // Change precision to help with jitter when user just rests their fingers //
        view?.setScaleX(scaleFactor)
        view?.setScaleY(scaleFactor)
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        inScale = true
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        inScale = false
    }

    init {
        gestureScale = ScaleGestureDetector(c, this)
    }
}