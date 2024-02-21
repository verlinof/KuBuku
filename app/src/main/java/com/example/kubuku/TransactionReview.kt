package com.example.kubuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kubuku.databinding.ActivityTransactionBookReviewBinding

class TransactionReview : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBookReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBookReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnConfirm.setOnClickListener {
                startActivity(Intent(this@TransactionReview, TransactionPayment::class.java))
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}