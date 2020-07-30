package com.example.moviedemo.repository.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["movieID"], unique = true)])
data class FavMovieModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val movieID: Int = -1,
    val title: String = "noname"
)