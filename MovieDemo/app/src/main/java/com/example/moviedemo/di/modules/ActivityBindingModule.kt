package com.example.moviedemo.di.modules

import com.example.moviedemo.screen.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

//enable inject for followings activity:
@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMain(): MainActivity
}