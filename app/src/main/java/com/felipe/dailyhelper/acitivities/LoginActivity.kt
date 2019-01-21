package com.felipe.dailyhelper.acitivities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.database.repository.UserRepository
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponents()

        btnLogin.setOnClickListener {
            val user = etUsername.text.toString()
            val password = etPassword.text.toString()
            if (validateUser(user, password)) {
                if (UserRepository.getInstance(this).login(user, password)) {

                } else {
                    tvError.visibility = View.VISIBLE
                }
            }
        }
        etUsername.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutUsername.isErrorEnabled = false
            tvError.visibility = View.GONE
        })
        etPassword.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutPassword.isErrorEnabled = false
            tvError.visibility = View.GONE
        })
    }

    private fun initComponents() {
        btnLogin = findViewById(R.id.login)
        layoutUsername = findViewById(R.id.input_layout_username)
        layoutPassword = findViewById(R.id.input_layout_password)
        etUsername = findViewById(R.id.input_edit_text_username)
        etPassword = findViewById(R.id.input_edit_text_password)
        tvError = findViewById(R.id.tv_invalid_user_password)
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
