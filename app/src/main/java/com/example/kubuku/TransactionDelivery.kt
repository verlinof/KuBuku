package com.example.kubuku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kubuku.databinding.ActivityTransactionDeliveryBinding

class TransactionDelivery : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}