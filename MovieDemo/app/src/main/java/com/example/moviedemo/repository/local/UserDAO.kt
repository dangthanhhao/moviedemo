package com.example.moviedemo.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*

const val CURRENT_USER_ID=1L
@Dao
interface UserDao{
    @Insert
    fun insert(user: UserModel)
    @Update
    fun update(user: UserModel)
    @Query("SELECT * FROM usermodel WHERE userID=:userID LIMIT 1")
    fun get(userID: Long):LiveData<UserModel>
    @Query("SELECT * FROM usermodel")
    fun getALL():LiveData<List<UserModel>>

}
//custom extention DAO
fun UserDao.getCurrentUser():LiveData<UserModel>{
    return get(CURRENT_USER_ID)
}