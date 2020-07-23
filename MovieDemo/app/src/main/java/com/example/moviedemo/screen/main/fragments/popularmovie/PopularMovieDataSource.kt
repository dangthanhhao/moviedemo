package com.example.moviedemo.screen.main.fragments.popularmovie

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.Movie
import com.example.moviedemo.repository.network.NetworkState
import timber.log.Timber

class PopularMovieDataSource(val repository: Repository) : PageKeyedDataSource<Int, Movie>() {

    private var page = 1
    val networkState = MutableLiveData<NetworkState>()

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.RUNING)
        repository.getPopularMovie().subscribe({
            Timber.i("Load first time")
            callback.onResult(it.movies, null, page + 1)
            networkState.postValue(NetworkState.LOADED)
        }, {
            networkState.postValue(NetworkState.FAILED)
            it.printStackTrace()
        }
        )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.RUNING)
        repository.getPopularMovie(params.key).subscribe({
            if (it.total_pages >= params.key) {
                callback.onResult(it.movies, params.key + 1)
                networkState.postValue(NetworkState.LOADED)
                Timber.i("Load more! $")
            } else {
                networkState.postValue(NetworkState.NO_MORE_ROW)
            }
        }, {
            it.printStackTrace()
            networkState.postValue(NetworkState.FAILED)
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

}