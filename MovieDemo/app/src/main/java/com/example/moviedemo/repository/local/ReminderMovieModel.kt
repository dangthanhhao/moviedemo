package com.example.moviedemo.repository.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = ["movieID"], unique = true)])
data class ReminderMovieModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var movieID: Int = -1,
    var title: String = "No name",
    var releaseDate: String = "2020",
    var reminderDate: Date = Date(),
    var imgURL: String = "",
    var rating: Double = 10.0
)