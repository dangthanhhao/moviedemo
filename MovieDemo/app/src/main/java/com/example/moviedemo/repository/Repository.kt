package com.example.moviedemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.moviedemo.repository.local.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Repository(val context: Context){
    private lateinit var userDAO:UserDao

    init {
        userDAO=Database.getInstance(context).UserDao
    }

    fun getUserProfile(): LiveData<UserModel>{
//        userDAO.checkCurrentUser().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).oner


        return LiveDataReactiveStreams.fromPublisher(userDAO.getCurrentUser()
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()))
    }

    fun updateUserProfile(user: UserModel){
        userDAO.update(user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe()
    }
}