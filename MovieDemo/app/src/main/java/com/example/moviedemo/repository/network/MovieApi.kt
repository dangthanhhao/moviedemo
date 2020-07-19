package com.example.moviedemo.repository.network


import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "0267c13d8c7d1dcddb40001ba6372235"


private val retrofit = Retrofit.Builder()
    .client(OkHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .baseUrl(
    BASE_URL
).build()

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): Observable<PopularMoviesResponse>


    companion object {
        val retrofitService: MovieApi by lazy {
            retrofit.create(MovieApi::class.java)
        }
    }
}