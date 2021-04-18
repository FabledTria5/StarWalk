package com.example.nasatoday.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PictureOfTheDayModel (
    @SerializedName("date") val date : String,
    @SerializedName("explanation") val explanation : String,
    @SerializedName("hdurl") val hdurl : String,
    @SerializedName("media_type") val media_type : String,
    @SerializedName("title") val title : String,
    @SerializedName("url") val url : String
) : Serializable