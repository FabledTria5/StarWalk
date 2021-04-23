package com.example.nasatoday.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.nasatoday.R
import com.example.nasatoday.databinding.ActivityMainBinding
import com.example.nasatoday.utils.TemporaryData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (TemporaryData.theme != R.style.Theme_Space) setTheme(TemporaryData.theme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
        postponeEnterTransition()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController = navController)
        binding.bottomNavigation.setOnNavigationItemReselectedListener {}
    }

}