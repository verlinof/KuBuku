package com.example.kubuku.models

data class Bundling(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val author: String = "",
    val rating: Double = 0.0,
    val priceDays: Int = 0,
    val priceWeeks: Int = 0,
    val stock: Int = 0,
    val totalOrder: Int = 0
)
