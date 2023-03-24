package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataTVSeries

data class SearchedTVSeries(
    val backdrop_path: String,
    val poster_path: String,
    val original_language: String,
    val original_name: String,
    val name: String,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int
)