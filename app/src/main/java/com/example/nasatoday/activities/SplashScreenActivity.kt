package com.example.nasatoday.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nasatoday.R

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIMEOUT = 2350L
        private const val ACTIVITY_TIMEOUT = 700L
    }

    private lateinit var transitionText: TextView
    private lateinit var transitionImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)

        transitionText = findViewById(R.id.transitionText)
        transitionImage = findViewById(R.id.transitionImage)

        startApp()
    }

    private fun startApp() = Handler(Looper.getMainLooper()).postDelayed({
        Intent(this, MainActivity::class.java).also {

            val textPair = Pair<View, String>(transitionText, "textTransition")
            val imagePair = Pair<View, String>(transitionImage, "imageTransition")
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, textPair, imagePair)

            startActivity(it, options.toBundle())
            Handler(Looper.getMainLooper()).postDelayed({ finish() }, ACTIVITY_TIMEOUT)
        }
    }, SPLASH_TIMEOUT)
}