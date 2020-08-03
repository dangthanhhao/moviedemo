package com.example.moviedemo.screen.main.fragments.detail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.ReminderMovieModel
import com.example.moviedemo.repository.network.Movie
import com.example.moviedemo.util.NotificationSetter
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    val repository: Repository,
    val application: Application
) : ViewModel() {
    val movie = MutableLiveData<Movie>()
    val isLoading = MutableLiveData<Boolean>()
    lateinit var reminder: LiveData<ReminderMovieModel>


    val ratingString = Transformations.map(movie, {
        it.vote_average.toString() + "/10"
    })

    fun initReminder(movieid: Int) {
        reminder=repository.getReminder(movieid)
    }
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
    fun setReminder(reminderDate:Date){
        Timber.i("Viewmodel got reminder date $reminderDate")
        with(movie.value!!){
            var newReminder=reminder.value
            if (newReminder==null)
                    newReminder=ReminderMovieModel(0,id,title,release_date,reminderDate,poster_path,vote_average)
            newReminder.reminderDate=reminderDate
            repository.insertOrUpdateReminder(newReminder)
            NotificationSetter.setNotification(id, title, application, reminderDate)
        }
    }
}