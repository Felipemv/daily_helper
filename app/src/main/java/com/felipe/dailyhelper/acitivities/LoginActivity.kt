package com.felipe.dailyhelper.acitivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.database.repository.UserRepository
import com.felipe.dailyhelper.util.PreferencesUtil
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : Activity() {

    private lateinit var btnLogin: Button
    private lateinit var layoutUsername: TextInputLayout
    private lateinit var layoutPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var tvError: TextView
    private lateinit var cbKeepLoggedIn: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponents()

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (validateUser(username, password)) {
                val user = UserRepository.getInstance(this).login(username, password)

                if (user != null) {

                    if (cbKeepLoggedIn.isChecked) {
                        PreferencesUtil(this).userId = user.id
                        PreferencesUtil(this).username = user.username
                        PreferencesUtil(this).password = user.password
                    }

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    tvError.visibility = View.VISIBLE
                }
            }
        }
        etUsername.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutUsername.isErrorEnabled = false
            tvError.visibility = View.INVISIBLE
        })
        etPassword.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutPassword.isErrorEnabled = false
            tvError.visibility = View.INVISIBLE
        })
    }

    private fun initComponents() {
        btnLogin = findViewById(R.id.login)
        layoutUsername = findViewById(R.id.input_layout_username)
        layoutPassword = findViewById(R.id.input_layout_password)
        etUsername = findViewById(R.id.input_edit_text_username)
        etPassword = findViewById(R.id.input_edit_text_password)
        tvError = findViewById(R.id.tv_invalid_user_password)
        cbKeepLoggedIn = findViewById(R.id.cb_keep_logged_in)
    }

    private fun validateUser(user: String, password: String): Boolean {
        var isValid = true
        if (user.isEmpty()) {
            layoutUsername.isErrorEnabled = true
            layoutUsername.error = "Username cannot be empty"
            isValid = false
        }

        if (password.isEmpty()) {
            layoutUsername.isErrorEnabled = true
            layoutPassword.error = "Password cannot be empty"
            isValid = false
        }

        return isValid
    }
}
