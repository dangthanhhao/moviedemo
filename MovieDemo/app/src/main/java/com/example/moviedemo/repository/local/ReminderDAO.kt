package com.example.moviedemo.repository.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ReminderDAO {
    @Query("select * from remindermoviemodel")
    fun getAll(): Flowable<List<ReminderMovieModel>>

    @Insert
    fun insert(reminder: ReminderMovieModel): Completable

    @Delete
    fun delete(reminder: ReminderMovieModel): Completable

    @Update
    fun update(reminder: ReminderMovieModel): Completable
}