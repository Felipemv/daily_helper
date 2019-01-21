package com.felipe.dailyhelper.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.database.entities.User
import com.felipe.dailyhelper.database.repository.UserRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var layoutFullName: TextInputLayout
    private lateinit var layoutUsername: TextInputLayout
    private lateinit var layoutPassword: TextInputLayout
    private lateinit var layoutEmail: TextInputLayout
    private lateinit var layoutPhone: TextInputLayout
    private lateinit var etFullName: TextInputEditText
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        initComponents()

        btnRegister.setOnClickListener {
            val name = etFullName.text.toString()
            val user = etUsername.text.toString()
            val password = etPassword.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()

            if (validateUser(name, user, password, email, phone)) {
                if (UserRepository.getInstance(this).exists(user)) {
                    tvError.visibility = View.VISIBLE

                } else {
                    UserRepository.getInstance(this).insert(User(name, user, password, email, phone))
                    Toast.makeText(this, "Your user is created!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
        etFullName.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutFullName.isErrorEnabled = false
            tvError.visibility = View.INVISIBLE
        })
        etUsername.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutUsername.isErrorEnabled = false
            tvError.visibility = View.INVISIBLE
        })
        etPassword.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutPassword.isErrorEnabled = false
            tvError.visibility = View.INVISIBLE
        })
        etEmail.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutEmail.isErrorEnabled = false
            tvError.visibility = View.INVISIBLE
        })
        etPhone.addTextChangedListener({ _: CharSequence?, _: Int, _: Int, _: Int ->
            layoutPhone.isErrorEnabled = false
            tvError.visibility = View.INVISIBLE
        })
    }

    private fun initComponents() {
        btnRegister = findViewById(R.id.register)
        layoutFullName = findViewById(R.id.input_layout_name)
        layoutUsername = findViewById(R.id.input_layout_username)
        layoutPassword = findViewById(R.id.input_layout_password)
        layoutEmail = findViewById(R.id.input_layout_email)
        layoutPhone = findViewById(R.id.input_layout_phone)
        etFullName = findViewById(R.id.input_edit_text_name)
        etUsername = findViewById(R.id.input_edit_text_username)
        etPassword = findViewById(R.id.input_edit_text_password)
        etEmail = findViewById(R.id.input_edit_text_email)
        etPhone = findViewById(R.id.input_edit_text_phone)
        tvError = findViewById(R.id.tv_invalid_fields)
    }

    private fun validateUser(name: String, user: String, password: String, email: String, phone: String): Boolean {
        var isValid = true
        if (name.isEmpty()) {
            layoutFullName.isErrorEnabled = true
            layoutFullName.error = "Name cannot be empty"
            isValid = false
        }

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

        if (email.isEmpty()) {
            layoutEmail.isErrorEnabled = true
            layoutEmail.error = "Email cannot be empty"
            isValid = false
        }

        if (phone.isEmpty()) {
            layoutPhone.isErrorEnabled = true
            layoutPhone.error = "Phone cannot be empty"
            isValid = false
        }

        return isValid
    }
}
