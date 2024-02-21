package com.example.kubuku.models

data class Cart (
    val idUser: String = "",
    val idProducts: ArrayList<String> = ArrayList<String>()
)