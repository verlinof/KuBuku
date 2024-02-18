package com.example.kubuku.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kubuku.OnboardingActivity
import com.example.kubuku.R
import com.example.kubuku.databinding.FragmentProfileBinding
import com.example.kubuku.helper.HelperSharedPreferences
import com.example.kubuku.page.EditProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var helperSharedPreferences: HelperSharedPreferences
    private lateinit var username: String
    private lateinit var currentUser: FirebaseUser
    private var profilePicture: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()

        currentUser = auth.currentUser!!

        //Username
        helperSharedPreferences = HelperSharedPreferences(requireContext())
        username = helperSharedPreferences.getUsername().toString()
        profilePicture = currentUser.photoUrl

        with(binding) {
            //Set User data
            tvProfileName.text = username
            tvEmail.text = currentUser!!.email
            if(profilePicture != null) {
                Glide.with(ivProfilePicture)
                    .load(profilePicture)
                    .centerCrop()
                    .into(ivProfilePicture)
            }

            //Button
            //Button Edit Profile
            btnEditProfile.setOnClickListener {
                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
            }

            //Logout
            btnLogOut.setOnClickListener {
                //Sign Out
                auth.signOut()

                //Sign Out Shared Prefference
                helperSharedPreferences.signOut()

                Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show()

                startActivity(Intent(requireContext(), OnboardingActivity::class.java))
                activity?.finishAffinity()
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}