package com.example.kubuku.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kubuku.TransactionAddress
import com.example.kubuku.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(layoutInflater)

        with(binding) {
            btnDone.setOnClickListener {
                startActivity(Intent(requireContext(), TransactionAddress::class.java))
            }
        }

        return binding.root
    }

}