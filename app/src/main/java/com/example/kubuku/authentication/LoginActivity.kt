package com.example.kubuku.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kubuku.R
import com.example.kubuku.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun login() {
        with(binding) {
//        if(checkInputField()) {
//            auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString()).addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful) {
//                    val currentUser = auth.currentUser
//                }else {
//                    Toast.makeText(requireContext(), "Login Failed, Check your Email or Password", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }else {
//            Toast.makeText(requireContext(), "Check all the input!", Toast.LENGTH_SHORT).show()
//        }
        }
    }

//    private fun checkInputField():Boolean {
//        with(binding) {
//            if(etEmail.text.toString() == "") {
//                etEmail.error = "This Field is Required"
//                return false
//            }
//            if(etPassword.text.toString() == "") {
//                etPassword.error = "This Field is Required"
//                return false
//            }
//        }
//
//        return true
//    }
}