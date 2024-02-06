package com.example.kubuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kubuku.databinding.ActivitySplashBinding
import com.example.kubuku.page.DashboardActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        with(binding) {
            ivLogo.alpha = 0f
            ivLogo.animate().setDuration(1500).alpha(1f).withEndAction() {
                if(currentUser != null) {
                    startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                    finishAffinity()
                }else {
                    startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
                    finish()
                }
            }
        }
    }
}