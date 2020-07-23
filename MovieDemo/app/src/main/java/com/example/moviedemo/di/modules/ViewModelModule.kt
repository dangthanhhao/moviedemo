package com.example.moviedemo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedemo.di.util.ViewModelKey
import com.example.moviedemo.screen.UserProfileViewModel
import com.example.moviedemo.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserProfileVM(vm: UserProfileViewModel): ViewModel

    @Singleton
    @Binds
    abstract fun bindVMFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}