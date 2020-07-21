package com.example.moviedemo.screen.main.fragments.movie

import android.annotation.SuppressLint
import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.Movie
import com.example.moviedemo.repository.network.NetworkState
import com.example.moviedemo.repository.network.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PopularMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy{ Repository(application)}

    val movies =   MutableLiveData<MutableList<Movie>>()

    val networkState=MutableLiveData<NetworkState>()
    init {
        movies.value= mutableListOf()
        networkState.postValue(NetworkState.RUNING)
        getMovies()
    }

    @SuppressLint("CheckResult")
    public fun getMovies(page : Int=1) {

        repository.getPopularMovie(page)
            .observeOn(AndroidSchedulers.mainThread()).
            timeout(3, TimeUnit.SECONDS)
            .onErrorReturn {
                it.printStackTrace()
                return@onErrorReturn null
            }
            .subscribeOn(Schedulers.io())
            .subscribe({
                if(it.movies.isNotEmpty()){
                        movies.value!!.addAll(it.movies)
                        movies.postValue(movies.value)
                    networkState.postValue(NetworkState.LOADED)
                }

            }, {
                it.printStackTrace()
            }, {
                Timber.i("Complete")
            })
    }

}