package com.example.moviedemo.screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class UserProfileViewModelFactory (
    private val application: Application
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)){
            return UserProfileViewModel(application) as T
        }
    throw IllegalArgumentException("Unknown ViewModel request to build")
    }
}