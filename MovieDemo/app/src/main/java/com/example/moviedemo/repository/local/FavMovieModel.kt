package com.example.moviedemo.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavMovieModel(
    @PrimaryKey
    val movieID: Int
)