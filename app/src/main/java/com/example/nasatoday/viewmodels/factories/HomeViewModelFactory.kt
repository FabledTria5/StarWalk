package com.example.nasatoday.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasatoday.repository.NasaRepository
import com.example.nasatoday.viewmodels.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: NasaRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository = repository) as T
    }

}