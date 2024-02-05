package com.example.kubuku.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kubuku.R
import com.example.kubuku.databinding.ActivityDashboardBinding
import com.example.kubuku.fragment.HomeFragment
import com.example.kubuku.fragment.DiscoveryFragment
import com.example.kubuku.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            navbarDashboard.selectedItemId = R.id.itemHome
            replaceFragment(HomeFragment())

            navbarDashboard.setOnItemSelectedListener() {
                when(it.itemId) {
                    R.id.itemHome -> replaceFragment(HomeFragment())
                    R.id.itemDiscover -> replaceFragment(DiscoveryFragment())
                    R.id.itemTransaction -> replaceFragment(DiscoveryFragment())
                    R.id.itemProfile -> replaceFragment(ProfileFragment())
                    else -> {}
                }
                true
            }
        }
    }

    //Function
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}