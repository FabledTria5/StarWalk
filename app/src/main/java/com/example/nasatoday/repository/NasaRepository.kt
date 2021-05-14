package com.example.nasatoday.repository

import com.example.nasatoday.model.PictureOfTheDayResponse
import com.example.nasatoday.network.RetrofitInstance
import retrofit2.Call

class NasaRepository {

    fun getImage(apiKey: String, startDate: String, endDate: String): Call<PictureOfTheDayResponse> {
        return RetrofitInstance.api.getImage(
            key = apiKey,
            startDate = startDate,
            endDate = endDate
        )
    }

}