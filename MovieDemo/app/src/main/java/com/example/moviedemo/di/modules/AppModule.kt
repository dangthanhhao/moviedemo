package com.example.moviedemo.di.modules

import android.app.Application
import com.example.moviedemo.repository.local.Database
import com.example.moviedemo.repository.local.FavDAO
import com.example.moviedemo.repository.local.ReminderDAO
import com.example.moviedemo.repository.local.UserDAO
import com.example.moviedemo.repository.network.MovieApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideUserDao(application: Application): UserDAO {
        return Database.getInstance(application).UserDAO
    }

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        return MovieApi.retrofitService
    }

    @Singleton
    @Provides
    fun provideFavMovieDao(application: Application): FavDAO {
        return Database.getInstance(application).FavDAO
    }

    @Singleton
    @Provides
    fun provideReminderDao(application: Application): ReminderDAO {
        return Database.getInstance(application).ReminderDAO
    }
}