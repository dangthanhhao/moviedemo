package com.example.moviedemo.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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