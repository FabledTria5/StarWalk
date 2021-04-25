package com.example.nasatoday.network

import com.example.nasatoday.model.PictureOfTheDayModel
import com.example.nasatoday.model.PictureOfTheDayResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    fun getImage(
        @Query(value = "thumbs") thumbs: Boolean = true,
        @Query(value = "start_date") startDate: String,
        @Query(value = "end_date") endDate: String,
        @Query(value = "api_key") key: String
    ): Call<PictureOfTheDayResponse>

}