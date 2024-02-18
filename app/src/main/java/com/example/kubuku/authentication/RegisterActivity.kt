package com.example.kubuku.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import com.example.kubuku.databinding.ActivityRegisterBinding
import com.example.kubuku.helper.HelperSharedPreferences
import com.example.kubuku.page.DashboardActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var helperSharedPreferences: HelperSharedPreferences
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helperSharedPreferences = HelperSharedPreferences(this@RegisterActivity)

        with(binding) {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
            btnRegister.setOnClickListener {
                register()
            }
            btnHideUnhidePassword.setOnClickListener {
                val selection = etPassword.selectionEnd // Simpan posisi kursor

                if (etPassword.inputType != InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                } else {
                    etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }

                etPassword.setSelection(selection)
            }
            btnHideUnhideConfirmPassword.setOnClickListener {
                val selection = etConfirmPassword.selectionEnd // Simpan posisi kursor

                if (etConfirmPassword.inputType != InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                } else {
                    etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }

                etConfirmPassword.setSelection(selection)
            }
        }
    }

    private fun register() {
        if(checkInputField()) {
            with(binding) {
                var email = etEmail.text.toString()
                var password = etPassword.text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val user = auth.currentUser
                        setUserDetails(user!!)

                        startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java))
                        finishAffinity()
                    }else {
                        Toast.makeText(this@RegisterActivity, "Registration ${it.exception.toString()}", Toast.LENGTH_SHORT ).show()
                        Log.e("ERROR REGISTRATION", "register: ${it.exception.toString()}", )
                    }
                }
            }
        }
    }

    private fun setUserDetails(user: FirebaseUser) {
        val userData = HashMap<String, Any>()
        userData["email"] = binding.etEmail.text.toString()
        userData["username"] = binding.etUsername.text.toString()
        userData["phone"] = binding.etPhone.text.toString()
//        userData["profilePicture"] = "none"


        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("users").document(user.uid)
            .set(userData)
            .addOnCompleteListener {
                helperSharedPreferences.setUsername(binding.etUsername.text.toString())
                helperSharedPreferences.setPhone(binding.etPhone.text.toString())
//                helperSharedPreferences.setProfilePicture("none")
                Toast.makeText(this@RegisterActivity, "Account has been created successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@RegisterActivity, "Error : $e", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkInputField(): Boolean {
        var isValid = true

        // Define a list of EditTexts to iterate through
        with(binding) {
            val editTexts = listOf(etEmail, etUsername, etPhone, etPassword, etConfirmPassword)

            for (editText in editTexts) {
                val text = editText.text.toString().trim()

                if (text.isEmpty()) {
                    editText.error = "This Field is Required"
                    isValid = false
                } else if (editText == etEmail && !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    editText.error = "Check Email Format"
                    isValid = false
                }
            }

            // Check if password and confirm password match
            if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
                etPassword.error = "Password and Re-password need to be same"
                etConfirmPassword.error = "Password and Re-password need to be same"
                isValid = false
            }
        }

        return isValid
    }

}