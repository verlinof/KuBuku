package com.example.kubuku.models

data class Book(
    var id: String = "",
    val author: String = "",
    val description: String = "",
    val image: String = "",
    val priceDays: Int = 0,
    val priceWeeks: Int = 0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val title: String = "",
    val totalOrder: Int = 0
)