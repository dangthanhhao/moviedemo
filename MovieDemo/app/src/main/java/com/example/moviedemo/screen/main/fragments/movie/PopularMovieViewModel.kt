package com.example.moviedemo.screen.main.fragments.movie

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PopularMovieViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = Repository(application)
    val movies = MutableLiveData<List<Movie>>()

    init {
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

                        movies.value=it.movies

                }

            }, {
                it.printStackTrace()
            }, {
                Timber.i("Complete")
            })
    }

}