package com.example.nasatoday.model

sealed class PictureOfTheDayData {
    data class Success(val pictureOfTheDayResponse: PictureOfTheDayResponse) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
}
