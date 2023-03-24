package com.example.moviesgrab.networking

data class ResultItems(
    val page: Int,
    val results: List<ResultSingle>,
    val total_pages: Int,
    val total_results: Int
)