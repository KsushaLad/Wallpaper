package com.example.wallpaper

import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.view.WindowManager
import android.os.Bundle
import android.widget.TextView
import android.view.animation.Animation
import android.app.Activity
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.Nullable


class LogoActivity : Activity() {
    private var logo_animation: Animation? = null
    private var textView_animation: TextView? = null
    private var imageView_animation: ImageView? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo_activity)
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        initialization()
        startMainActivity()
    }

    private fun initialization() {
        logo_animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.logo_anim
        ) //загрузка анимации в переменную
        textView_animation = findViewById(R.id.image_anim_2)
        imageView_animation = findViewById(R.id.image_anim_1)
        val with = with(imageView_animation) { this?.startAnimation(logo_animation) }
        with(textView_animation) {
            this?.startAnimation(logo_animation)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    private fun startMainActivity() {
        Thread {
            //создание нового потока
            try {
                Thread.sleep(2500) //замирание потока
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val i = Intent(this@LogoActivity, MainActivity::class.java)
            startActivity(i)
        }.start()
    }
}