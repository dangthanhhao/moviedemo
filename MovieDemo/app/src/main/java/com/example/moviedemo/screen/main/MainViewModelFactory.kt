package com.example.moviedemo.screen.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedemo.repository.Repository
import java.lang.IllegalArgumentException

class MainViewModelFactory (
    private val application: Application
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application) as T
        }
    throw IllegalArgumentException("Unknown ViewModel request to build")
    }
}