package com.felipe.dailyhelper.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.database.repository.UserRepository

class SplashActivity : AppCompatActivity(), Runnable {

    private val DELAY = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed(this, DELAY)
    }

    override fun run() {
        if (UserRepository.getInstance(this).find() != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE

            val createAccount = findViewById<TextView>(R.id.create_account)
            createAccount.visibility = View.VISIBLE
            createAccount.setOnClickListener {
                startActivity(Intent(this, CreateAccountActivity::class.java))
            }

            val login = findViewById<Button>(R.id.btn_login)
            login.visibility = View.VISIBLE
            login.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }

    }
}
