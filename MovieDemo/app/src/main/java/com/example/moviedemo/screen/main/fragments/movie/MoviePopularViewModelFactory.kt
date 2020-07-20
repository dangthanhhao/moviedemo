package com.example.moviedemo.screen.main.fragments.movie

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MoviePopularViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularMovieViewModel::class.java)){
            return PopularMovieViewModel(app) as T
        }
        else throw IllegalArgumentException("Class requested is not PopularMovieViewModel")
    }
}