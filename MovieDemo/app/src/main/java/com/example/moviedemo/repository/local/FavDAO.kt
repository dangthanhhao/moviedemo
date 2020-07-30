package com.example.moviedemo.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable


@Dao
interface FavDAO {
    @Query("select * from FavMovieModel")
    fun getAllFavIDs(): Flowable<List<FavMovieModel>>

    @Insert
    fun insertFavID(item: FavMovieModel): Completable

    @Query("delete from FavMovieModel where movieID=:movieID")
    fun deleteFavID(movieID: Int): Completable
//
//    @Query("select * from FavMovieModel")
//    fun getAllFavMoviesDatasource(): DataSource.Factory<Int,Movie>
}