package com.example.nasatoday.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.nasatoday.R

@BindingAdapter("app:url")
fun loadImage(imageView: ImageView, imageURL: String?) {
    imageView.load(imageURL) {
        placeholder(R.drawable.ic_placeholder)
    }
}