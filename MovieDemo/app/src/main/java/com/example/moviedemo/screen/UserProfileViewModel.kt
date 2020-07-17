package com.example.moviedemo.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.UserModel
import timber.log.Timber
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class UserProfileViewModel (application: Application) : AndroidViewModel(application){
    private val repository= Repository(application)
    val userProfile:LiveData<UserModel> = repository.getUserProfile()


    val userDateOfBirthString= Transformations.map(userProfile){
        SimpleDateFormat("yyyy-MM-dd").format(it.dateOfBirth)
    }
    val userGenderString= Transformations.map(userProfile){
         if (it.gender) "Male" else "Female"
    }

    fun change(){

    }

    fun updateUserProfile(avatarUriStr:String?){
        avatarUriStr?.let{
            userProfile.value?.avatar=avatarUriStr
        }

        userProfile.value?.let { repository.updateUserProfile(user = it) }
    }
    fun onChangeDateOfBirth(date: Date){
        userProfile.value?.let { it.dateOfBirth=date }
    }

}