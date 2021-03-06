package com.example.moviedemo.repository.network


import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "0267c13d8c7d1dcddb40001ba6372235"
public const val BASE_IMAGE_URL="https://image.tmdb.org/t/p/w185/"

private val retrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()
    )
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

    @GET ("movie/{movieid}")
    fun getMovieDetail(@Path("movieid") movieid:Int, @Query("api_key") apiKey: String = API_KEY)
    : Observable<Movie>

    companion object {
        val retrofitService: MovieApi by lazy {
            retrofit.create(MovieApi::class.java)
        }
    }
}