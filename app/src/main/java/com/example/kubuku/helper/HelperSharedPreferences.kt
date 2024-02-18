package com.example.kubuku.helper

import android.content.Context
import android.content.SharedPreferences

class HelperSharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun signOut() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

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

    fun setProfilePicture(profilePicture: String) {
        val editor = sharedPreferences.edit()
        editor.putString("profilePicture", profilePicture)
        editor.apply()
    }

    fun getProfilePicture(): String? {
        return sharedPreferences.getString("profilePicture", "none")
    }

}