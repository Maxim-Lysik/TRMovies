package com.example.moviesgrab.networking

import com.example.moviesgrab.Constants
import com.example.moviesgrab.networking.person.PopularPersons
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies.ResultMoviesList
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataTVSeries.ResultTVSeriesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {



    //GETTING TRENDING MOVIES RESULTS

    @GET("3/trending/movie/week?api_key=" + Constants.API_KEY_OUR)
    suspend fun fetch_trending_items_movie(): Response<ResultItems>

    //GETTING TRENDING TV SERIES RESULTS

    @GET("3/trending/tv/week?api_key=" + Constants.API_KEY_OUR)
    suspend fun fetch_trending_items_tv(): Response<ResultItems>


    //GETTING TV SERIES BY ID

    @GET("3/tv/{tv_id}?api_key=" + Constants.API_KEY_OUR)
    suspend fun fetch_tv_series_by_id(@Path("tv_id") questionId: String?): Response<TvSeriesSingle>  // ??


    //GETTING MOVIES BY ID

    @GET("3/movie/{movie_id}?api_key=" + Constants.API_KEY_OUR)
    suspend fun fetch_movies_by_id(@Path("movie_id") questionId: String?): Response<TvSeriesSingle>  // ??


    @GET("3/movie/{movie_id}?api_key=" + Constants.API_KEY_OUR)
    suspend fun fetch_movies_by_id_for_saving(@Path("movie_id") questionId: String?): Response<MovieResultSingle>  // ??


    //GETTING POPULAR ACTORS

    @GET("3/person/popular?api_key=" + Constants.API_KEY_OUR + "&language=en-US&page=1")
    suspend fun fetch_popular_persons(): Response<PopularPersons>

    // LOOKING FOR A REQUIRED MOVIE


    // Test1

    @GET("3/search/movie?api_key=${Constants.API_KEY_OUR}&language=en-US&query=blank&page=1&include_adult=false")
    suspend fun fetch_searched_movies(@Query("query") userId: String, @Query("page") page: Int): Response<ResultMoviesList>


    // LOOKING FOR A REQUIRED TV-SERIES

    @GET("3/search/tv?api_key=${Constants.API_KEY_OUR}&language=en-US&query=blank&page=1&include_adult=false")
    suspend fun fetch_searched_tv_series(@Query("query") userId: String, @Query("page") page: Int ): Response<ResultTVSeriesList>


    /*@GET("3/search/movie?api_key=" + Constants.TMDB_API_KEY + "&language=en-US&query={movie_name}&page=1&include_adult=false")
    suspend fun fetch_searched_movies(@Path("movie_name") questionId: String?, @Query("page") page: Int = 1): Response<ResultMoviesList>


*/

   /* @GET("3/movie/{movie_id}?api_key=" + Constants.TMDB_API_KEY)
    suspend fun fetcch_movies_by_id(@Path("movie_id") questionId: String?): Response<TvSeriesSingle>  // ??

*/



    // GETTING IMAGE

   /* @GET("/questions/{questionId}?key=" + Constants.TMDB_API_KEY + "&site=stackoverflow&filter=withbody")
    suspend fun questionDetails(@Path("questionId") questionId: String?): Response<ResultSingle>


*/

    //GETTING FILM BY ID

    /*



    */


    /*


  @GET("/questions?key=" + Constants.TMDB_API_KEY + "&order=desc&sort=activity&site=stackoverflow")
    suspend fun lastActiveQuestions(@Query("pagesize") pageSize: Int?): Response<ResultItems>

    @GET("/questions/{questionId}?key=" + Constants.TMDB_API_KEY + "&site=stackoverflow&filter=withbody")
    suspend fun questionDetails(@Path("questionId") questionId: String?): Response<ResultSingle>





    * */

/*


 //GETTING TRENDING RESULTS

    @GET("3/trending/all/day?api_key=" + Constants.TMDB_API_KEY)
    suspend fun fetch_trending_items(): Response<ResultItems>


* */




}