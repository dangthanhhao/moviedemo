package com.example.moviedemo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedemo.di.util.ViewModelKey
import com.example.moviedemo.screen.UserProfileViewModel
import com.example.moviedemo.screen.main.fragments.about.AboutViewModel
import com.example.moviedemo.screen.main.fragments.detail.MovieDetailViewModel
import com.example.moviedemo.screen.main.fragments.favourite.FavViewModel
import com.example.moviedemo.screen.main.fragments.popularmovie.PopularMovieViewModel
import com.example.moviedemo.screen.main.fragments.reminder.ReminderViewModel
import com.example.moviedemo.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
abstract class ViewModelModule {
    //
    @Singleton
    @Binds
    abstract fun bindVMFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    //list all viewmodel can produce by factory
    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserProfileVM(vm: UserProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PopularMovieViewModel::class)
    abstract fun bindPopularMovieVM(vm: PopularMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailVM(vm: MovieDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FavViewModel::class)
    abstract fun bindFavMovieVM(vm: FavViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReminderViewModel::class)
    abstract fun bindReminder(vm: ReminderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindAbout(vm: AboutViewModel): ViewModel
}