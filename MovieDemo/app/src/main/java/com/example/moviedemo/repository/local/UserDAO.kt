package com.example.moviedemo.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

const val CURRENT_USER_ID=1L

@Dao
interface UserDAO {
    @Insert
    fun insert(user: UserModel): Completable

    @Update
    fun update(user: UserModel): Completable

    @Query("SELECT * FROM usermodel WHERE userID=:userID LIMIT 1")
    fun get(userID: Long): Flowable<UserModel>

    @Query("SELECT * FROM usermodel")
    fun getALL(): LiveData<List<UserModel>>


    @Query("SELECT * FROM usermodel WHERE userID=:userID LIMIT 1")
    fun checkUserID(userID: Long) :Maybe<UserModel>

}

//custom extention DAO
fun UserDAO.getCurrentUser(): Flowable<UserModel> {
    return get(CURRENT_USER_ID)
}

//custom extention DAO
fun UserDAO.checkCurrentUser(): Maybe<UserModel> {
    return checkUserID(CURRENT_USER_ID)
}