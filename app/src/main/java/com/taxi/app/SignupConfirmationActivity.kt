package com.taxi.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignupConfirmationActivity : AppCompatActivity() {

    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_confirmation)

        btnNext = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            // Переход на LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
