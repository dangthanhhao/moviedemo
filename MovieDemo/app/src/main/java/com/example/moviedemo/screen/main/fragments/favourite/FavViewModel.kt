package com.example.moviedemo.screen.main.fragments.favourite

import androidx.lifecycle.ViewModel
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.Movie
import io.reactivex.Observable
import javax.inject.Inject

class FavViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    val favIDs = repository.getFavMovies()


    init {

    }

    fun getMovieDetail(movieID: Int): Observable<Movie> {
        return repository.getMovieDetail(movieID)
    }

    fun setFavouriteMovie(id: Int) {
        repository.insertFavMovie(id)
    }
}