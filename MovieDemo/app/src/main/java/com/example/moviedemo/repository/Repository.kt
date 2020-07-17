package com.example.moviedemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.moviedemo.repository.local.Database
import com.example.moviedemo.repository.local.UserDao
import com.example.moviedemo.repository.local.UserModel
import com.example.moviedemo.repository.local.getCurrentUser
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Repository(val context: Context){
    private lateinit var userDAO:UserDao

    init {
        userDAO=Database.getInstance(context).UserDao
    }

    fun getUserProfile(): LiveData<UserModel>{
        return LiveDataReactiveStreams.fromPublisher(userDAO.getCurrentUser()
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()))
    }

    fun updateUserProfile(user: UserModel){
        userDAO.update(user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe()
    }
}