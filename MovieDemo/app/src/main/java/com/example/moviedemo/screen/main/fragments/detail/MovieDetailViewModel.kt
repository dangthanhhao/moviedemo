package com.example.moviedemo.screen.main.fragments.detail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.Movie
import timber.log.Timber
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(val repository: Repository) :ViewModel(){
    val movie= MutableLiveData<Movie>()
    val isLoading=MutableLiveData<Boolean>()

    val ratingString=Transformations.map(movie,{
        it.vote_average.toString()+"/10"
    })
    @SuppressLint("CheckResult")

    fun getMovie(id: Int) {
        repository.getMovieDetail(id).subscribe({
            movie.value = it

            Timber.i("Loaded detail movie $it")
            isLoading.value = false
        }, {
            it.printStackTrace()
        })
    }

    fun setFavouriteMovie(id: Int, title: String) {
        repository.insertFavMovie(id, title)
    }
}