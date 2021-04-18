package com.example.nasatoday.network

import com.example.nasatoday.model.PictureOfTheDayModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    fun getImage(@Query(value = "api_key") key: String): Call<PictureOfTheDayModel>

}