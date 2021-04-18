package com.example.nasatoday.repository

import com.example.nasatoday.model.PictureOfTheDayModel
import com.example.nasatoday.network.RetrofitInstance
import retrofit2.Call

class NasaRepository {

    fun getImage(apiKey: String): Call<PictureOfTheDayModel> {
        return RetrofitInstance.api.getImage(apiKey)
    }

}