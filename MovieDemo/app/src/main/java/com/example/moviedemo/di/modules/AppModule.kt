package com.example.moviedemo.di.modules

import android.app.Application
import com.example.moviedemo.repository.local.Database
import com.example.moviedemo.repository.local.UserDao
import com.example.moviedemo.repository.network.MovieApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideUserDao(application: Application): UserDao {
        return Database.getInstance(application).UserDao
    }

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        return MovieApi.retrofitService
    }
}