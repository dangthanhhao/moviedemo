package com.example.moviedemo.screen.main.fragments.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.ReminderMovieModel
import javax.inject.Inject

class ReminderViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    var listReminders: LiveData<List<ReminderMovieModel>> = repository.getAllReminders()
}