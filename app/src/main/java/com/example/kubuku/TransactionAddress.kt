package com.example.kubuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kubuku.databinding.ActivityTransactionAddressBinding

class TransactionAddress : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnConfirm.setOnClickListener {
                startActivity(Intent(this@TransactionAddress, TransactionReview::class.java))
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}