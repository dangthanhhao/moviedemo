package com.example.moviedemo.screen.main.fragments.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.FavMovieModel
import com.example.moviedemo.repository.network.Movie
import io.reactivex.Observable
import javax.inject.Inject

class FavViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    var originalFavIDS = repository.getFavMovies()
    var favIDs = MutableLiveData<List<FavMovieModel>>()

    var filterKeyword: String? = ""
    val filterObserver = Observer<List<FavMovieModel>> {
        filterList()
    }

    init {
        favIDs.postValue(mutableListOf())
        originalFavIDS.observeForever(filterObserver)
    }

    override fun onCleared() {
        originalFavIDS.removeObserver(filterObserver)
        super.onCleared()
    }

    fun getMovieDetail(movieID: Int): Observable<Movie> {
        return repository.getMovieDetail(movieID)
    }

    fun setFavouriteMovie(id: Int, title: String) {
        repository.insertFavMovie(id, title)
    }

    fun filterList() {
        if (filterKeyword.isNullOrEmpty()) {
            favIDs.value = originalFavIDS.value

        } else {
            favIDs.value = originalFavIDS.value?.filter {
                return@filter it.title.toLowerCase().contains(filterKeyword!!.toLowerCase())
            }
        }
    }

}