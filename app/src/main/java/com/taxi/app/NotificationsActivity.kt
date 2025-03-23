package com.taxi.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationsActivity : AppCompatActivity() {

    private lateinit var rvNotifications: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        rvNotifications = findViewById(R.id.rvNotifications)
        rvNotifications.layoutManager = LinearLayoutManager(this)

        // Пример списка уведомлений
        val notifications = listOf(
            NotificationItem("Скидка 10%", "Получите скидку на аренду при использовании промокода SPRING10", "Сегодня"),
            NotificationItem("Изменения в расписании", "Автомобиль BMW X5 будет доступен завтра", "Вчера"),
            NotificationItem("Приветственное сообщение", "Добро пожаловать в наше приложение!", "2 дня назад")
        )

        val adapter = NotificationsAdapter(notifications)
        rvNotifications.adapter = adapter
    }
}
