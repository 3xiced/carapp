package com.taxi.app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class SignupStep1Activity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var checkboxAgree: CheckBox
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_step1)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        checkboxAgree = findViewById(R.id.checkboxAgree)
        btnNext = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "@string/invalid_email_error"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "@string/password_hint"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                etConfirmPassword.error = "@string/password_doesnt_match_error"
                return@setOnClickListener
            }
            if (!checkboxAgree.isChecked) {
                Toast.makeText(this, "@string/confidential_not_accepted", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Если валидация пройдена, переходим ко второму шагу
            startActivity(Intent(this, SignupStep2Activity::class.java))
        }
    }
}
