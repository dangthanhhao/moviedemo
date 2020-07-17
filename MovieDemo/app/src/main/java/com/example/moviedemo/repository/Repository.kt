package com.example.moviedemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.moviedemo.repository.local.Database
import com.example.moviedemo.repository.local.UserDao
import com.example.moviedemo.repository.local.UserModel
import com.example.moviedemo.repository.local.getCurrentUser
import io.reactivex.rxjava3.core.Observable

class Repository(val context: Context){
    private lateinit var userDAO:UserDao

    init {
        userDAO=Database.getInstance(context).UserDao
    }
    fun getUserProfile(): LiveData<UserModel>{

        return userDAO.getCurrentUser()
    }
    fun updateUserProfile(user: UserModel){
        return userDAO.update(user)
    }
}