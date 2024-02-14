package com.example.kubuku.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kubuku.OnboardingActivity
import com.example.kubuku.R
import com.example.kubuku.databinding.FragmentProfileBinding
import com.example.kubuku.helper.HelperSharedPreferences
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var helperSharedPreferences: HelperSharedPreferences
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        //Username
        helperSharedPreferences = HelperSharedPreferences(requireContext())
        username = helperSharedPreferences.getUsername().toString()

        with(binding) {
            tvProfileName.text = username
            tvEmail.text = currentUser!!.email

        }

        // Inflate the layout for this fragment
        return binding.root
    }

}