package com.example.moviedemo.screen.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.UserModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel (application: Application) : AndroidViewModel(application){
    private val repository= Repository(application)
    val userProfile:LiveData<UserModel> = repository.getUserProfile()

    val userDateOfBirthString= Transformations.map(userProfile){
        SimpleDateFormat("yyyy-MM-dd").format(it.dateOfBirth)
    }
    val userGenderString= Transformations.map(userProfile){
         if (it.gender) "Male" else "Female"
    }

    fun change(){
        userProfile.value?.email  = "aszqsc@gmail.com"
        userProfile.value?.let { repository.updateUserProfile(user = it) }
    }
}