package com.example.kubuku.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.text.InputType
import android.view.View
import android.widget.Toast
import com.example.kubuku.databinding.ActivityLoginBinding
import com.example.kubuku.helper.HelperSharedPreferences
import com.example.kubuku.page.DashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var helperSharedPreferences: HelperSharedPreferences
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helperSharedPreferences = HelperSharedPreferences(this@LoginActivity)

        with(binding) {
            btnLogin.setOnClickListener {
                showLoading()
                login()
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            btnHideUnhide.setOnClickListener {
                val selection = etPassword.selectionEnd // Simpan posisi kursor

                if (etPassword.inputType != InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                } else {
                    etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }

                etPassword.setSelection(selection)
            }
        }
    }

    //FUNCTION
    private fun login() {
        with(binding) {
            if(checkInputField()) {
                auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString()).addOnCompleteListener {
                        task ->
                    if(task.isSuccessful) {
                        val currentUser = auth.currentUser

                        hideLoading()

                        getUserCredentials(currentUser!!)
                        Toast.makeText(this@LoginActivity, "Welcome back, ${currentUser!!.email}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finishAffinity()
                    }else {
                        hideLoading()

                        Toast.makeText(this@LoginActivity, "Login Failed, Check your Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                hideLoading()
                Toast.makeText(this@LoginActivity, "Check all the input!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserCredentials(user: FirebaseUser) {
        firestore.collection("users").document(user.uid)
            .get()
            .addOnSuccessListener {  user ->
                if(user.exists()) {
                    val data = user.data
                    helperSharedPreferences.setUsername(data!!["username"].toString())
                    helperSharedPreferences.setPhone(data!!["phone"].toString())
                }
            }
    }

    private fun checkInputField():Boolean {
        with(binding) {
            if(etEmail.text.toString() == "" && etPassword.text.toString() == "") {
                etEmail.error = "This Field is Required"
                etPassword.error = "This Field is Required"
                return false
            }
            if(etEmail.text.toString() == "") {
                etEmail.error = "This Field is Required"
                return false
            }
            if(etPassword.text.toString() == "") {
                etPassword.error = "This Field is Required"
                return false
            }
        }

        return true
    }

    private fun showLoading() {
        binding.loadingBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingBar.visibility = View.INVISIBLE
    }
}