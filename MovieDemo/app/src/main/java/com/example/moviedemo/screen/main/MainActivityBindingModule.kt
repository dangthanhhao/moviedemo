package com.example.moviedemo.screen.main

import com.example.moviedemo.screen.main.fragments.about.AboutFragment
import com.example.moviedemo.screen.main.fragments.detail.MovieDetailFragment
import com.example.moviedemo.screen.main.fragments.favourite.FavFragment
import com.example.moviedemo.screen.main.fragments.popularmovie.PopularMovieFragment
import com.example.moviedemo.screen.main.fragments.reminder.ReminderFragment
import com.example.moviedemo.screen.main.fragments.setting.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBindingModule {
    //enable inject for following fragments in main activity:
    @ContributesAndroidInjector
    abstract fun about(): AboutFragment

    @ContributesAndroidInjector
    abstract fun favourite(): FavFragment

    @ContributesAndroidInjector
    abstract fun popular(): PopularMovieFragment

    @ContributesAndroidInjector
    abstract fun setting(): SettingFragment

    @ContributesAndroidInjector
    abstract fun moviedetail(): MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun reminder(): ReminderFragment

}