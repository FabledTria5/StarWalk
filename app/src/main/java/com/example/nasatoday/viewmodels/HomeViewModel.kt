package com.example.nasatoday.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasatoday.BuildConfig
import com.example.nasatoday.model.PictureOfTheDayData
import com.example.nasatoday.model.PictureOfTheDayResponse
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

    private val startDaysOffset = 2

    fun getImage() {
        if (imageResponse.value != null) return
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank())
            imageResponse.value = PictureOfTheDayData.Error(Throwable("No API key"))
        else {
            repository.getImage(
                apiKey,
                getStartDate(),
                currentTime.value!!
            ).enqueue(object : Callback<PictureOfTheDayResponse> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponse>,
                    response: Response<PictureOfTheDayResponse>
                ) {
                    imageResponse.value = response.body()?.let { PictureOfTheDayData.Success(it) }
                }

                override fun onFailure(call: Call<PictureOfTheDayResponse>, t: Throwable) {
                    imageResponse.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }

    fun getTime() = viewModelScope.launch {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = Date(System.currentTimeMillis())
        currentTime.value = formatter.format(date)
    }

    private fun getStartDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = Date(System.currentTimeMillis() - startDaysOffset * 24 * 60 * 60 * 1000)
        return formatter.format(date)
    }
}