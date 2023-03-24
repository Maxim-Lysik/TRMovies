package com.example.moviesgrab

import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.networking.person.KnownFor
import com.example.moviesgrab.networking.person.SinglePerson

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/"

    val item1_shm = ResultSingle(true, "", listOf(2,3), 1, "", "", 0.0, "", "", "", "", true, 0.0, 1 )
    val item2_shm = ResultSingle(true, "", listOf(2,3), 1, "", "", 0.0, "", "", "", "", true, 0.0, 1 )
    val item3_shm = ResultSingle(true, "", listOf(2,3), 1, "", "", 0.0, "", "", "", "", true, 0.0, 1 )
    val item4_shm = ResultSingle(true, "", listOf(2,3), 1, "", "", 0.0, "", "", "", "", true, 0.0, 1 )
    val item5_shm = ResultSingle(true, "", listOf(2,3), 1, "", "", 0.0, "", "", "", "", true, 0.0, 1 )
    val item6_shm = ResultSingle(true, "", listOf(2,3), 1, "", "", 0.0, "", "", "", "", true, 0.0, 1 )


    val known_for1 = KnownFor(true, "", "", 1, "", "", "", "", "", "", "", "", true, 1.2, 1)
    //val person1 = SinglePerson(true, 1, 1, "", "", 1.2, "")

    val person1 = SinglePerson(true, 1, 1, listOf(known_for1), "", "", 1.2, "")
// TMDB_API_KEY

    const val API_KEY_OUR = BuildConfig.API_KEY
}