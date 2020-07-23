package com.example.moviedemo.di.component

import android.app.Application
import com.example.moviedemo.MovieDemoApplication
import com.example.moviedemo.di.modules.ActivityBindingModule
import com.example.moviedemo.di.modules.AppModule
//import com.example.moviedemo.di.modules.ActivityBindingModule
import com.example.moviedemo.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ViewModelModule::class, ActivityBindingModule::class, AppModule::class])
interface AppComponent : AndroidInjector<MovieDemoApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun getApplication(application: Application): Builder

        fun build(): AppComponent
    }
}