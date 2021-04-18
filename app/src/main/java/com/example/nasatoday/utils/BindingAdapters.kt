package com.example.nasatoday.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation

@BindingAdapter("app:url")
fun loadImage(imageView: ImageView, imageURL: String?) {
    imageView.load(imageURL) {
        transformations(RoundedCornersTransformation(radius = 30f))
    }
}