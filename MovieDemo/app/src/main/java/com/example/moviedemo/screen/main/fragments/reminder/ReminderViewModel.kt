package com.example.moviedemo.screen.main.fragments.reminder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.ReminderMovieModel
import com.example.moviedemo.util.NotificationSetter
import javax.inject.Inject

class ReminderViewModel @Inject constructor(
    val repository: Repository,
    val application: Application
) : ViewModel() {

    var listReminders: LiveData<List<ReminderMovieModel>> = repository.getAllReminders()

    fun deleteReminder(movieID: Int, title: String) {
        repository.deleteReminder(movieID)
        NotificationSetter.removeNotification(movieID, title, application)
    }

}