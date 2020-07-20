package com.example.moviedemo.screen.main.fragments.movie

import android.annotation.SuppressLint
import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PopularMovieViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = Repository(application)
    val movies = MutableLiveData<MutableList<Movie>>()


    init {
        movies.value= mutableListOf()
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
                }

            }, {
                it.printStackTrace()
            }, {
                Timber.i("Complete")
            })
    }

}