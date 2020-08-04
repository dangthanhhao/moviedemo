package com.example.moviedemo.screen.main.fragments.about

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AboutViewModel @Inject constructor() : ViewModel() {
    val BASE_URL = "https://www.themoviedb.org/about/our-history"
}