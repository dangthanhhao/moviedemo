package com.example.moviedemo.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ReminderDAO {
    @Query("select * from remindermoviemodel")
    fun getAll(): Flowable<List<ReminderMovieModel>>

    @Insert
    fun insert(reminder: ReminderMovieModel): Completable

    @Query("delete from remindermoviemodel where movieID=:movieid")
    fun delete(movieid: Int): Completable

    @Update
    fun update(reminder: ReminderMovieModel): Completable

    @Query("select * from remindermoviemodel where movieID=:movieid limit 1")
    fun getReminder(movieid: Int): Flowable<ReminderMovieModel>

    @Query("select * from remindermoviemodel order by reminderDate desc limit 2")
    fun get2Recent(): Flowable<List<ReminderMovieModel>>
}