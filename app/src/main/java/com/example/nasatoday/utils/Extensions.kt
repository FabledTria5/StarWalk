package com.example.nasatoday.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.regex.Matcher
import java.util.regex.Pattern

const val pattern =
    "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*"

fun Fragment.toast(string: String) =
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun String.getYoutubeUrl(): String {

    val compiledPattern = Pattern.compile(pattern)
    val matcher: Matcher =
        compiledPattern.matcher(this)

    if (matcher.find()) {
        return matcher.group()
    }
    return this
}
