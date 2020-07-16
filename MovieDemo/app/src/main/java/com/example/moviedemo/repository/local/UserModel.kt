package com.example.moviedemo.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class UserModel(

    @PrimaryKey(autoGenerate = true)
    var userID:Long=0L,
    var name:String="",
    var dateOfBirth:Date=Date(),
    var email:String="",
    var gender:Boolean=true,
    var avatar:String=""
)