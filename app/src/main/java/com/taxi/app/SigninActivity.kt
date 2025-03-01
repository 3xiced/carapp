package com.taxi.app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.common.SignInButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class SigninActivity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnSignIn: Button
//    private lateinit var btnGoogleSignIn: SignInButton
    private lateinit var btnSignUp: Button
    private lateinit var tvForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // Получение ссылок на элементы интерфейса
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
//        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                updateSignInButtonState()
            }
        }
        etEmail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)

        // Обработчик кнопки "@string/login_button"
        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Введите корректный email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "@string/password_hint"
                return@setOnClickListener
            }
            btnSignIn.isEnabled = false  // на время запроса блокируем кнопку

            // Выполнение GET запроса в фоновом потоке с использованием корутин
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url = URL("https://www.google.com")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000
                    connection.connect()
                    val responseCode = connection.responseCode
                    withContext(Dispatchers.Main) {
                        btnSignIn.isEnabled = true
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            Toast.makeText(this@SigninActivity, "OK", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@SigninActivity, "Ошибка: $responseCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        btnSignIn.isEnabled = true
                        Toast.makeText(this@SigninActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Обработчик для кнопки "@string/login_button через Google"
//        btnGoogleSignIn.setOnClickListener {
//        }

        // Обработчик для кнопки "@string/signup_button"
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignupStep1Activity::class.java))
        }

        // Обработчик для "Забыли пароль?"
        tvForgotPassword.setOnClickListener {
        }
    }

    // Обновление состояния кнопки "@string/login_button"
    private fun updateSignInButtonState() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        btnSignIn.isEnabled = email.isNotEmpty() && password.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
