package com.example.moviedemo.repository.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.moviedemo.repository.network.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface MovieDAO {
    @Insert
    fun insert(movie: Movie): Completable

    @Update
    fun update(movie: Movie): Completable

    @Query("SELECT * FROM movie WHERE id=:movieid LIMIT 1")
    fun getLiveMovie(movieid: Int): Flowable<Movie>

    @Query("SELECT * FROM movie")
    fun getALL(): Flowable<List<Movie>>


    @Query("SELECT * FROM movie WHERE id=:movieid LIMIT 1")
    fun checkUserID(movieid: Int): Maybe<Movie>

}
////custom extention DAO
//fun UserDao.getCurrentUser(): Flowable<UserModel> {
//    return get(CURRENT_USER_ID)
//}
//
////custom extention DAO
//fun UserDao.checkCurrentUser(): Maybe<UserModel> {
//    return checkUserID(CURRENT_USER_ID)
//}