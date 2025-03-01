package com.taxi.app

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val titleText = findViewById<TextView>(R.id.titleText)
        val subtitleText = findViewById<TextView>(R.id.subtitleText)
        val logoImage = findViewById<ImageView>(R.id.logoImage)

        // Анимация появления
        ObjectAnimator.ofPropertyValuesHolder(
            titleText,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationX", -50f, 0f)
        ).apply {
            duration = 1
            start()
        }

        ObjectAnimator.ofPropertyValuesHolder(
            subtitleText,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationX", -50f, 0f)
        ).apply {
            duration = 1
            start()
        }

        ObjectAnimator.ofFloat(logoImage, "alpha", 0f, 1f).apply {
            duration = 1
            start()
        }


        // Получаем ссылки на кнопки из разметки
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Обработчик кнопки "@string/login_button" - открываем SigninActivity
        btnLogin.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        // Обработчик кнопки "@string/signup_button" - открываем SignupActivity
        btnRegister.setOnClickListener {
            startActivity(Intent(this, SignupStep1Activity::class.java))
        }
    }
}
