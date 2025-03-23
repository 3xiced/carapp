package com.taxi.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var ivAvatar: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvJoinDate: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnChangePassword: Button
    private lateinit var btnLogout: Button

    companion object {
        private const val PICK_AVATAR_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ivAvatar = findViewById(R.id.ivAvatar)
        tvUserName = findViewById(R.id.tvUserName)
        tvJoinDate = findViewById(R.id.tvJoinDate)
        tvEmail = findViewById(R.id.tvEmail)
        tvGender = findViewById(R.id.tvGender)
        tvPassword = findViewById(R.id.tvPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)
        btnLogout = findViewById(R.id.btnLogout)

        // Пример статических данных
        tvUserName.text = "Иван Иванов"
        tvJoinDate.text = "Присоединился в январе 2023"
        tvEmail.text = "ivan@example.com"
        tvGender.text = "Мужской"

        // Обработчик нажатия на аватарку – открывает галерею для выбора нового изображения
        ivAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_AVATAR_REQUEST)
        }

        // Обработчик смены пароля
        btnChangePassword.setOnClickListener {
            // Логика изменения пароля
        }

        // Обработчик выхода из профиля – переходит на SigninActivity
        btnLogout.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            // Очищаем стек активностей
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_AVATAR_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            imageUri?.let {
                ivAvatar.setImageURI(it)
            }
        }
    }
}
