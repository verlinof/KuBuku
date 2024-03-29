package com.example.kubuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kubuku.databinding.ActivityTransactionPaymentBinding

class TransactionPayment : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnConfirm.setOnClickListener {
                startActivity(Intent(this@TransactionPayment, TransactionDelivery::class.java))
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}