package com.example.moviesgrab.networking

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultSingle(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    //@SerializedName("title") val first_air_date: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>?,
    @SerializedName("id") val id: Int?,
    //@SerializedName("title") val media_type: String,
   // @SerializedName("title") val name: String,
    //@SerializedName("title") val origin_country: List<String>,
    //@SerializedName("title") val original_language: String,
   // @SerializedName("title") val original_name: String,
    @SerializedName("original_title") val original_title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("release_date") val release_date: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val vote_average: Double?,
    @SerializedName("vote_count") val vote_count: Int?
): Serializable