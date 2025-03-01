package com.taxi.app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class SignupStep2Activity : AppCompatActivity() {

    private lateinit var etLastName: TextInputEditText
    private lateinit var etFirstName: TextInputEditText
    private lateinit var etMiddleName: TextInputEditText
    private lateinit var etBirthDate: TextInputEditText
    private lateinit var rgGender: RadioGroup
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_step2)

        etLastName = findViewById(R.id.etLastName)
        etFirstName = findViewById(R.id.etFirstName)
        etMiddleName = findViewById(R.id.etMiddleName)
        etBirthDate = findViewById(R.id.etBirthDate)
        rgGender = findViewById(R.id.rgGender)
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

        btnNext.setOnClickListener {
            if (etLastName.text.toString().trim().isEmpty() ||
                etFirstName.text.toString().trim().isEmpty() ||
                etBirthDate.text.toString().trim().isEmpty() ||
                rgGender.checkedRadioButtonId == -1) {
                Toast.makeText(this, "@string/fill_all_fields_error", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, SignupStep3Activity::class.java))
        }
    }
}
