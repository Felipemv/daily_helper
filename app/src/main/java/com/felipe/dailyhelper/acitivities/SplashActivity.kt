package com.felipe.dailyhelper.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.felipe.dailyhelper.R

class SplashActivity : AppCompatActivity(), Runnable {

    private val DELAY = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed(this, DELAY)
    }

    override fun run() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
