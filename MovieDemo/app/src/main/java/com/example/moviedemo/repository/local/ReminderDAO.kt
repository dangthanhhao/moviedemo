package com.example.moviedemo.repository.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

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
    fun getReminder(movieid:Int):Flowable<ReminderMovieModel>
}