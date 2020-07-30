package com.example.moviedemo.repository.local

import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    fun deleteFavID(item: FavMovieModel): Completable
//
//    @Query("select * from FavMovieModel")
//    fun getAllFavMoviesDatasource(): DataSource.Factory<Int,Movie>
}