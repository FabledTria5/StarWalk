package com.example.nasatoday.model

sealed class PictureOfTheDayData {
    data class Success(val pictureOfTheDayModel: PictureOfTheDayModel) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
}
