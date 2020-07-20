package com.example.moviedemo.repository

import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams


import com.example.moviedemo.repository.local.*
import com.example.moviedemo.repository.network.BASE_IMAGE_URL

import com.example.moviedemo.repository.network.MovieApi
import com.example.moviedemo.repository.network.PopularMoviesResponse
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Repository(val context: Context) {
    private lateinit var userDAO: UserDao
    private lateinit var movieApi: MovieApi
    init {
        userDAO = Database.getInstance(context).UserDao
        movieApi=MovieApi.retrofitService
    }

    fun getUserProfile(): LiveData<UserModel> {
        //An action check if current user is created in DB. If not, create (insert) a row and then return the row (current user)
        val action = userDAO.checkCurrentUser()
            .switchIfEmpty(Maybe.just(UserModel())).flatMapCompletable {
//                Timber.i("Current user ${it.toString()}")
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


    fun updateUserProfile(user: UserModel) {
        userDAO.update(user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getPopularMovie(page:Int=1): Observable<PopularMoviesResponse> {
        return movieApi.getPopularMovies(page = page)
    }


    companion object{
        fun getUrLImage(relativeURL :String): String {
            return BASE_IMAGE_URL+relativeURL.substring(1)
        }
    }

}