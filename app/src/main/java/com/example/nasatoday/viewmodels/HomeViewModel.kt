package com.example.nasatoday.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasatoday.BuildConfig
import com.example.nasatoday.model.PictureOfTheDayData
import com.example.nasatoday.model.PictureOfTheDayModel
import com.example.nasatoday.repository.NasaRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val repository: NasaRepository) : ViewModel() {

    val imageResponse: MutableLiveData<PictureOfTheDayData> = MutableLiveData()
    val currentTime: MutableLiveData<String> = MutableLiveData()

    fun getImage() {
        if (imageResponse.value != null) return
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank())
            imageResponse.value = PictureOfTheDayData.Error(Throwable("No API key"))
        else {
            repository.getImage(apiKey).enqueue(object : Callback<PictureOfTheDayModel> {
                override fun onResponse(
                    call: Call<PictureOfTheDayModel>,
                    response: Response<PictureOfTheDayModel>
                ) {
                    imageResponse.value = response.body()?.let { PictureOfTheDayData.Success(it) }
                }

                override fun onFailure(call: Call<PictureOfTheDayModel>, t: Throwable) {
                    imageResponse.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }

    fun getTime() {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("yyyy.mm.dd", Locale.ENGLISH)
            val date = Date(System.currentTimeMillis())
            currentTime.value = formatter.format(date)
        }
    }

}