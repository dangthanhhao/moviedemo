package com.example.moviedemo.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.UserModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val userProfile: LiveData<UserModel> = repository.getUserProfile()


    val userDateOfBirthString = Transformations.map(userProfile) {
        SimpleDateFormat("yyyy-MM-dd").format(it.dateOfBirth)
    }
    val userGenderString = Transformations.map(userProfile) {
        if (it.gender) "Male" else "Female"
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