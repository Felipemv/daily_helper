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
import com.felipe.dailyhelper.util.PreferencesUtil

class SplashActivity : AppCompatActivity(), Runnable {

    private val DELAY = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val username = PreferencesUtil(this).username
        val password = PreferencesUtil(this).password

        if ((username != null && !username.isEmpty()) || (password != null && !password.isEmpty())) {
            val user = UserRepository.getInstance(this).login(username!!, password!!)

            if (user != null) {
                PreferencesUtil(this).userId = user.id
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        val handler = Handler()
        handler.postDelayed(this, DELAY)
    }

    override fun run() {
        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE

        val createAccount = findViewById<TextView>(R.id.create_account)
        createAccount.visibility = View.VISIBLE
        createAccount.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        val login = findViewById<Button>(R.id.btn_login)
        login.visibility = View.VISIBLE
        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
