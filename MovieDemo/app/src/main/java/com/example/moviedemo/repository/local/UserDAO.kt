package com.example.moviedemo.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

const val CURRENT_USER_ID=1L
@Dao
interface UserDao{
    @Insert
    fun insert(user: UserModel): Completable
    @Update
    fun update(user: UserModel) : Completable
    @Query("SELECT * FROM usermodel WHERE userID=:userID LIMIT 1")
    fun get(userID: Long): Flowable<UserModel>
    @Query("SELECT * FROM usermodel")
    fun getALL():LiveData<List<UserModel>>


    @Query("SELECT * FROM usermodel WHERE userID=:userID LIMIT 1")
    fun checkUserID(userID: Long) :Maybe<UserModel>

}
//custom extention DAO
fun UserDao.getCurrentUser():Flowable<UserModel>{
    return get(CURRENT_USER_ID)
}

//custom extention DAO
fun UserDao.checkCurrentUser():Maybe<UserModel>{
    return checkUserID(CURRENT_USER_ID)
}