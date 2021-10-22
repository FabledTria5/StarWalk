package com.example.nasatoday.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.nasatoday.R
import com.example.nasatoday.databinding.ActivityMainBinding
import com.example.nasatoday.utils.Constants.MY_PREFERENCES
import com.example.nasatoday.utils.Constants.THEME_PREFERENCE
import com.example.nasatoday.utils.Themes

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        switchTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
        postponeEnterTransition()
    }

    private fun switchTheme() {
        getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).also { prefs ->
            Themes.values()[prefs.getInt(THEME_PREFERENCE, 1)].themeResource.also {
                setTheme(it)
            }
        }
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController = navController)
    }

}