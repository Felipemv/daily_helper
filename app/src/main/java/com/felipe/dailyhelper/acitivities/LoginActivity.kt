package com.felipe.dailyhelper.acitivities

import android.app.Activity
import android.os.Bundle
import com.felipe.dailyhelper.R

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


}
