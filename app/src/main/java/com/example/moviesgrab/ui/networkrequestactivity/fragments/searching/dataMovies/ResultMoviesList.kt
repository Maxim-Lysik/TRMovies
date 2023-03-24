package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies

import com.example.moviesgrab.networking.ResultSingle

data class ResultMoviesList(
    val page: Int,
    val results: MutableList<ResultSingle>,
    val total_pages: Int,
    val total_results: Int
)