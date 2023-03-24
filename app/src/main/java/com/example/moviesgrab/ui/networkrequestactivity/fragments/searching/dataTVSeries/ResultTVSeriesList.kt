package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataTVSeries

data class ResultTVSeriesList(
    val page: Int,
    val results: MutableList<SearchedTVSeries>,
    val total_pages: Int,
    val total_results: Int
)