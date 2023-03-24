package com.example.moviesgrab.networking

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvSeriesSingle(
    @SerializedName("adult")val adult: Boolean,
    @SerializedName("backdrop_path")val backdrop_path: String,
    //val created_by: List<CreatedBy>,
   // val episode_run_time: List<Any>,
    @SerializedName("first_air_date")val first_air_date: String,
    //val genres: List<GenreX>,
    @SerializedName("homepage")val homepage: String,
    @SerializedName("id")val id: Int,
    @SerializedName("in_production")val in_production: Boolean,
   // val languages: List<String>,
    @SerializedName("last_air_date")val last_air_date: String,
   // val last_episode_to_air: LastEpisodeToAir,
    @SerializedName("name")val name: String,
   // val networks: List<Network>,
   // val next_episode_to_air: Any,
    @SerializedName("number_of_episodes")val number_of_episodes: Int,
    @SerializedName("number_of_seasons")val number_of_seasons: Int,
  //  val origin_country: List<String>,
    @SerializedName("original_language")val original_language: String,
    @SerializedName("original_name")val original_name: String,
    @SerializedName("overview")val overview: String,
    @SerializedName("popularity")val popularity: Double,
    @SerializedName("poster_path")val poster_path: String,
  //  val production_companies: List<ProductionCompany>,
  //  val production_countries: List<ProductionCountry>,
  //  val seasons: List<Season>,
  //  val spoken_languages: List<SpokenLanguage>,
    @SerializedName("status")val status: String,
    @SerializedName("tagline")val tagline: String,
    @SerializedName("type")val type: String,
    @SerializedName("vote_average")val vote_average: Double,
    @SerializedName("vote_count")val vote_count: Int
): Serializable