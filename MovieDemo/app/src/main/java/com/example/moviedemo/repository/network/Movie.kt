package com.example.moviedemo.repository.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie (

    @SerializedName("popularity") val popularity: Double = 2.toDouble(),
    @SerializedName("vote_count") val vote_count : Int=1,
    @SerializedName("video") val video : Boolean=true,
    @SerializedName("poster_path") val poster_path : String="",
    @SerializedName("id") val id : Int=1,
    @SerializedName("adult") val adult : Boolean=false,
    @SerializedName("backdrop_path") val backdrop_path : String="",
    @SerializedName("original_language") val original_language : String="en",
    @SerializedName("original_title") val original_title : String="Unknown",
    @SerializedName("genre_ids") val genre_ids : List<Int> = listOf(),
    @SerializedName("title") val title : String = "Unknown",
    @SerializedName("vote_average") val vote_average : Double=1.0,
    @SerializedName("overview") val overview : String="Lorem",
    @SerializedName("release_date") val release_date : String="2020"
)