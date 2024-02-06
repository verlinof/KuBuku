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
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()

        with(binding) {
            btnLogout.setOnClickListener {
                auth.signOut()
                startActivity(Intent(requireContext(), OnboardingActivity::class.java))
                activity?.finishAffinity()
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}