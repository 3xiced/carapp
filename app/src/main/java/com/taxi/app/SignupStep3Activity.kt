package com.taxi.app

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.Calendar

class SignupStep3Activity : AppCompatActivity() {

    private lateinit var etLicenseNumber: TextInputEditText
    private lateinit var etBirthDate: TextInputEditText
    private lateinit var ivLicensePhoto: ImageView
    private lateinit var ivPassportPhoto: ImageView

    private lateinit var btnNext: android.widget.Button

    // Храним URI выбранных изображений (если нужно для отправки)
    private var licensePhotoUri: Uri? = null
    private var passportPhotoUri: Uri? = null

    // Регистрируем запуск выбора изображения для ВУ
    private val licensePhotoLauncher = registerForActivityResult(GetContent()) { uri: Uri? ->
        uri?.let {
            licensePhotoUri = it
            ivLicensePhoto.setImageURI(it)
        }
    }

    // Регистрируем запуск выбора изображения для паспорта
    private val passportPhotoLauncher = registerForActivityResult(GetContent()) { uri: Uri? ->
        uri?.let {
            passportPhotoUri = it
            ivPassportPhoto.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_step3)

        etLicenseNumber = findViewById(R.id.etLicenseNumber)
        etBirthDate = findViewById(R.id.etLicenseIssueDate)
        ivLicensePhoto = findViewById(R.id.ivLicensePhoto)
        ivPassportPhoto = findViewById(R.id.ivPassportPhoto)
        btnNext = findViewById(R.id.btnNext)

// При нажатии показываем календарь
        etBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // Форматируем выбранную дату в формат dd.MM.yyyy
                etBirthDate.setText(String.format("%02d.%02d.%04d", selectedDay, selectedMonth + 1, selectedYear))
            }, year, month, day).show()
        }

// Если пользователь редактирует дату вручную, автоматически добавляем точки
        etBirthDate.addTextChangedListener(object : TextWatcher {
            private var isEditing = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true

                // Удаляем все точки для корректной обработки
                var input = s.toString().replace(".", "")
                // Ограничиваем длину до 8 цифр (ddMMyyyy)
                if (input.length > 8) input = input.substring(0, 8)

                // Автоматически вставляем точки после дня (2 цифры) и месяца (еще 2 цифры)
                val builder = StringBuilder(input)
                if (builder.length > 2) builder.insert(2, ".")
                if (builder.length > 5) builder.insert(5, ".")

                val formatted = builder.toString()
                // Обновляем текст поля, если изменился
                if (formatted != s.toString()) {
                    etBirthDate.setText(formatted)
                    etBirthDate.setSelection(formatted.length)
                }
                isEditing = false
            }
        })


        // По нажатию на placeholder для фото водительского удостоверения
        ivLicensePhoto.setOnClickListener {
            // Запускаем выбор изображения (фильтр на изображения)
            licensePhotoLauncher.launch("image/*")
        }

        // По нажатию на placeholder для фото паспорта
        ivPassportPhoto.setOnClickListener {
            passportPhotoLauncher.launch("image/*")
        }

        btnNext.setOnClickListener {
            // Проверяем, что все поля заполнены
            if (etLicenseNumber.text.toString().trim().isEmpty() ||
                etBirthDate.text.toString().trim().isEmpty() ||
                licensePhotoUri == null ||
                passportPhotoUri == null) {
                Toast.makeText(this, "Заполните все поля и загрузите обе фотографии", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Отправляем GET-запрос на google.com в фоне
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
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            startActivity(Intent(this@SignupStep3Activity, SignupConfirmationActivity::class.java))
                        } else {
                            Toast.makeText(this@SignupStep3Activity, "Ошибка: $responseCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignupStep3Activity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
