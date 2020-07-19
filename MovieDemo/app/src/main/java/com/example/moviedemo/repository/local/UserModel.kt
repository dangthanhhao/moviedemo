package com.example.moviedemo.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class UserModel(

    @PrimaryKey(autoGenerate = true)
    var userID:Long=0L,
    var name:String="Unknown User",
    var dateOfBirth:Date=Date(),
    var email:String="YourEmail@email.com",
    var gender:Boolean=true,
    var avatar:String=""
)