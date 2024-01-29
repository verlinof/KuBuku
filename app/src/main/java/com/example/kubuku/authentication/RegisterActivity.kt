package com.example.kubuku.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kubuku.R
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    private fun register() {
//        if(checkInputField()) {
//            Firebase.auth.createUserWithEmailAndPassword(email, password) .addOnCompleteListener {
//                if(it.isSuccessful) {
//                    val user = Firebase.auth.currentUser
//                    setRole(user!!)
//
//                    startActivity(Intent(requireContext(), HomeUserActivity::class.java))
//                    activity?.finish()
//
//                }else {
//                    Log.e("Registration", it.exception.toString() )
//                }
//            }
//        }
    }

    private fun setUserDetails() {
//        val userData = HashMap<String, Any>()
//        userData["email"] = binding.etEmail.text.toString()
//        userData["username"] = binding.etUsername.text.toString()
//        userData["role"] = helper.getStatus().toString()
//
//        val firebase = FirebaseFirestore.getInstance()
//        firebase.collection("users").document(user.uid)
//            .set(userData)
//            .addOnCompleteListener {
//                helper.setUsername(binding.etUsername.text.toString())
//                Toast.makeText(requireContext(), "Account has been created successfully", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_SHORT).show()
//            }
    }

//    private fun checkInputField(): Boolean {
//        with(binding) {
//            val email = etEmail.text.toString()
//            if(etEmail.text.toString() == "") {
//                etEmail.error = "This Field is Required"
//                return false
//            }
//            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                etEmail.error = "Check Email Format"
//            }
//            if(etUsername.text.toString() == "") {
//                etUsername.error = "This Field is Required"
//                return false
//            }
//            if(etPassword.text.toString() == "") {
//                etPassword.error = "This Field is Required"
//                return false
//            }
//            if(etRePassword.text.toString() == "") {
//                etRePassword.error = "This Field is Required"
//                return false
//            }
//            if(etPassword.text.toString() != etRePassword.text.toString()) {
//                etPassword.error = "Password and Re-password need to be same"
//                etRePassword.error = "Password and Re-password need to be same"
//
//                return false
//            }
//
//            return true
//        }
//    }
}