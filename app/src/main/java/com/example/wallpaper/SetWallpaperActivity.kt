package com.example.wallpaper

import android.view.ScaleGestureDetector

import android.view.MotionEvent

import android.graphics.drawable.BitmapDrawable



import android.widget.Toast



import android.app.WallpaperManager

import android.view.WindowManager

import android.os.Bundle

import android.annotation.SuppressLint
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.Button
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_set_wallpaper.*
import okio.IOException



class SetWallpaperActivity : AppCompatActivity() {
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor = 0.1f
    //private lateinit var binding: ScriptGroup.Binding


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_wallpaper)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val wallpaperManager = WallpaperManager.getInstance(applicationContext)
        val set: Button = findViewById(R.id.set)
        val intent = intent
        Glide.with(this).load(getIntent().getStringExtra("image")).into(zoom)
        //imageView.setOnTouchListener(new MyScaleGestures(this)); //первая реализация
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener()) //вторая реализация
        set.setOnClickListener { view ->
            Toast.makeText(this@SetWallpaperActivity, "DONE", Toast.LENGTH_SHORT).show()
            val bitmap = (zoom.getDrawable() as BitmapDrawable).bitmap
            try {
                wallpaperManager.setBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mScaleGestureDetector!!.onTouchEvent(event)
    }

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 1.5f))
            zoom.setScaleX(mScaleFactor)
            zoom.setScaleY(mScaleFactor)
            return true
        }
    }
}