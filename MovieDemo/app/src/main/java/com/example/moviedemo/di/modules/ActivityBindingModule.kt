package com.example.moviedemo.di.modules

import com.example.moviedemo.screen.main.MainActivity
import com.example.moviedemo.screen.main.MainActivityBindingModule
import com.example.moviedemo.screen.profile.ProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

//enable inject for followings activity:
@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainActivityBindingModule::class])
    abstract fun bindMain(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindProfile(): ProfileActivity
}