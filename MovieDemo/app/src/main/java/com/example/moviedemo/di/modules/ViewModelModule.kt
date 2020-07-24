package com.example.moviedemo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedemo.di.util.ViewModelKey
import com.example.moviedemo.screen.UserProfileViewModel
import com.example.moviedemo.screen.main.fragments.detail.MovieDetailViewModel
import com.example.moviedemo.screen.main.fragments.popularmovie.PopularMovieViewModel
import com.example.moviedemo.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
abstract class ViewModelModule {
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

    @Singleton
    @Binds
    abstract fun bindVMFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}