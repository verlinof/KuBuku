package com.example.kubuku.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kubuku.databinding.FragmentDiscoveryBinding

class DiscoveryFragment : Fragment() {
    private lateinit var binding: FragmentDiscoveryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoveryBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

}