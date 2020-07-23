package com.example.moviedemo.screen.main.fragments.popularmovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviedemo.repository.Repository

class PopularMovieViewModel(application: Application) : AndroidViewModel(application) {
    val repository by lazy { Repository(application) }


    val listFactory =
        PopularMovieDataSourceFactory(
            repository
        )
    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()
    val moviePagedList = LivePagedListBuilder(listFactory, config).build()


    init {

    }


}