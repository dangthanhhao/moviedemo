package com.example.moviedemo.screen.main.fragments.popularmovie

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviedemo.repository.Repository
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(val repository: Repository) : ViewModel() {
//    val repository by lazy { Repository(Database.getInstance(application).UserDao, MovieApi.retrofitService) }

    val listFactory = PopularMovieDataSourceFactory(repository)
    val listFav = repository.getFavMovies()
    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()
    val moviePagedList = LivePagedListBuilder(listFactory, config).build()



    fun setFavouriteMovie(id: Int, title: String) {
        repository.insertFavMovie(id, title)
    }
}