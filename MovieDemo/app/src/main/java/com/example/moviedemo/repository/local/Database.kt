package com.example.moviedemo.repository.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

}

@androidx.room.Database(
    entities = [UserModel::class, FavMovieModel::class, ReminderMovieModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase(){
    abstract val UserDAO: UserDAO
    abstract val FavDAO: FavDAO
    abstract val ReminderDAO: ReminderDAO
//Singleton
    companion object{
        private var INSTANCE:Database?= null
        fun getInstance(context: Context): Database{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(context.applicationContext,Database::class.java,"User database")
                        .fallbackToDestructiveMigration()
//                        .allowMainThreadQueries()
                        .build()
                    INSTANCE=instance
                }
                return  instance
            }
        }
    }
}
