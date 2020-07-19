package com.example.moviedemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.moviedemo.repository.local.*
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import timber.log.Timber


class Repository(val context: Context) {
    private lateinit var userDAO: UserDao

    init {
        userDAO = Database.getInstance(context).UserDao
    }

    fun getUserProfile(): LiveData<UserModel> {
        Timber.i("Load userprofile")
        //An action check if current user is created in DB. If not, create (insert) a row and then return the row (current user)
        val action = userDAO.checkCurrentUser()
            .switchIfEmpty(Maybe.just(UserModel())).flatMapCompletable {
                Timber.i("Current user ${it.toString()}")
                if (it.userID == 0L)
                    return@flatMapCompletable userDAO.insert(UserModel())
                else
                    return@flatMapCompletable Completable.complete()
            }.andThen(
                userDAO.getCurrentUser()
            )
        return LiveDataReactiveStreams.fromPublisher(
            action
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        )
    }

    fun getUserProfile2(): LiveData<UserModel> {
        return LiveDataReactiveStreams.fromPublisher(
            userDAO.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        )
    }

    fun updateUserProfile(user: UserModel) {
        userDAO.update(user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe()
    }
}