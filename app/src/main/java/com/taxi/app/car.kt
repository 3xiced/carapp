package com.taxi.app

data class Car(
    val model: String,
    val brand: String,
    val pricePerDay: Int,
    val transmission: String,  // A/T или M/T
    val fuel: String,          // бензин, дизель, электричество
    val imageRes: Int          // ссылка на ресурс картинки
)
