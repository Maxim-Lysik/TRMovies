package com.example.moviesgrab.networking.person

data class PopularPersons(
    val page: Int,
    val results: List<SinglePerson>,
    val total_pages: Int,
    val total_results: Int
)