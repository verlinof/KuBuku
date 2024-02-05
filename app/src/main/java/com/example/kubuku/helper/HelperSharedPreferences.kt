package com.example.kubuku.helper

import android.content.Context
import android.content.SharedPreferences

class HelperSharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun setUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", "username")
    }

    fun setPhone(phone: String) {
        val editor = sharedPreferences.edit()
        editor.putString("phone", phone)
        editor.apply()
    }

    fun getPhone(): String? {
        return sharedPreferences.getString("phone", "-")
    }

}