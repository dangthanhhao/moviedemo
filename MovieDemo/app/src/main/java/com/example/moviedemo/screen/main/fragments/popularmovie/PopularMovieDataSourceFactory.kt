package com.example.moviedemo.screen.main.fragments.popularmovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.Movie
import com.example.moviedemo.repository.network.NetworkState

class PopularMovieDataSourceFactory(val repository: Repository) : DataSource.Factory<Int, Movie>() {
    val popularDataSource = MutableLiveData<PopularMovieDataSource>()
    val networkState =
        Transformations.switchMap<PopularMovieDataSource, NetworkState>(popularDataSource) {
            return@switchMap it.networkState
        }

    override fun create(): DataSource<Int, Movie> {
        val datasource =
            PopularMovieDataSource(
                repository
            )
        popularDataSource.postValue(datasource)
        return datasource
    }
}