package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesgrab.MoviesRepository
import com.example.moviesgrab.networking.FetchPopularPersons
import com.example.moviesgrab.networking.FetchSearchMoviesOrTvSeries
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.ui.common.Resource
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies.ResultMoviesList
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies.SearchedResultMovie
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataTVSeries.ResultTVSeriesList
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataTVSeries.SearchedTVSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchingViewModel @Inject constructor(
    private val fetchTrendingUseCase: FetchTrendingUseCase,
    private val fetchPopularPersons: FetchPopularPersons,
    private val fetchSearchMoviesOrTvSeries: FetchSearchMoviesOrTvSeries,
    private val savedStateHandle: SavedStateHandle,
    val moviesRepository: MoviesRepository
): ViewModel() {


    var is_Loading = false

    private var _result_searched_movies: MutableLiveData<List<ResultSingle>> = savedStateHandle.getLiveData("questions")
    val result_searched_movies: LiveData<List<ResultSingle>> get() = _result_searched_movies
    var pages_number_movies: Int = 0

    private var _result_searched_tv_series: MutableLiveData<List<SearchedTVSeries>> = savedStateHandle.getLiveData("questions2")
    val result_searched_tv_series: LiveData<List<SearchedTVSeries>> get() = _result_searched_tv_series
    var pages_number_tv_series: Int = 0

    var general_list: MutableLiveData<MutableList<FoundItem>> = savedStateHandle.getLiveData("questions3")
   // val general_list: LiveData<List<FoundItem>> get() = _general_list

    var searchNewsPage = 1
    var searchedMoviesResponse: ResultMoviesList? = null
    val searchTVSeries: MutableLiveData<Resource<ResultMoviesList>> = MutableLiveData()





    var temporalMutableList: MutableList<FoundItem> =mutableListOf <FoundItem>()

    fun getSearchedMoviesReal(name2: String, page: Int){
        viewModelScope.launch {

            is_Loading = true

            //var temporalMutableList: MutableList<FoundItem> =mutableListOf <FoundItem>()
            val result_searched_movies = fetchSearchMoviesOrTvSeries.fetch_searched_moives(name2, page)
            if (result_searched_movies is FetchSearchMoviesOrTvSeries.Result.Success){
                pages_number_movies = result_searched_movies.items.total_pages
                _result_searched_movies.value = result_searched_movies.items.results


                Log.d(
                    ContentValues.TAG,
                    "PAGES MOVIES ${pages_number_movies}"
                )

                for(i in result_searched_movies.items.results){
                    var temporary_found_tv_item: FoundItem
                    if(i.backdrop_path!=null) {
                        temporary_found_tv_item =
                            FoundItem(i.backdrop_path, i.title.toString(), i.id!!, i.popularity!!)
                      //  println("DOING SOME STUFF" + i.name)
                    } else {temporary_found_tv_item =
                        FoundItem(i.poster_path, i.title.toString(), i.id!!, i.popularity!!)}

                    temporalMutableList.add(temporary_found_tv_item)

                    general_list.value = temporalMutableList


                }




                Log.d(
                    ContentValues.TAG,
                    "GET SEARCHED MOVIES ${_result_searched_movies.value}"
                );


                general_list.value = temporalMutableList


                for (i in result_searched_movies.items.results) { println("NAME OF FOUND MOVIES "  + i.title + " MOVIE PATH" + i.overview ) }
            }else {
                  throw RuntimeException("fetch failed")
            }
        }
    }



   /* fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchTVSeries.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchTVSeries.postValue(handleSearchNewsResponse(response))
    }
*/

    fun searchItemsMovies(searchQuery: String) = viewModelScope.launch {
        //temporalMutableList.clear()

        val response = moviesRepository.getSearchedMovies(searchQuery, searchNewsPage )
        searchTVSeries.postValue(handleSearchMoviesResponse(response))
    }


    private fun handleSearchMoviesResponse(response: Response<ResultMoviesList>) : Resource<ResultMoviesList> {
       // searchedMoviesResponse?.results?.clear()
       // searchedMoviesResponse = null
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if(searchedMoviesResponse == null) {
                    searchedMoviesResponse = resultResponse
                } else {
                    val oldArticles = searchedMoviesResponse?.results
                    val newArticles = resultResponse.results
                    oldArticles?.addAll(newArticles)

                   /* for(i in newArticles){
                        var temporary_found_tv_item: FoundItem
                        if(i.backdrop_path!=null) {
                            temporary_found_tv_item =
                                FoundItem(i.backdrop_path, i.title.toString(), i.id!!, i.popularity!!)
                            println("DOING SOME STUFF" + i.title)
                        } else {temporary_found_tv_item =
                            FoundItem(i.backdrop_path, i.title.toString(), i.id!!, i.popularity!!)}

                        temporalMutableList.add(temporary_found_tv_item)

                        general_list.value = temporalMutableList
                    }*/

                }
                return Resource.Success(searchedMoviesResponse ?: resultResponse)
            }
        } else{  Log.d(
            ContentValues.TAG,
            "GOT ERROR ${_result_searched_movies.value}"
        ) }
        return Resource.Error(response.message())
    }


/*
    fun getSearchedTVSeriesReal(name2: String, page: Int){
        viewModelScope.launch {

            is_Loading = true

            val result_searched_tv_series = fetchSearchMoviesOrTvSeries.fetch_searched_tvseries(name2, page)
            if (result_searched_tv_series is FetchSearchMoviesOrTvSeries.Result.SuccessTVSeries){
                pages_number_tv_series = result_searched_tv_series.items.total_pages
                _result_searched_tv_series.value = result_searched_tv_series.items.results

                Log.d(
                    ContentValues.TAG,
                    "PAGES TV ${pages_number_tv_series}"
                )


                for(i in result_searched_tv_series.items.results){
                    var temporary_found_tv_item: FoundItem
                    if(i.backdrop_path!=null) {
                        temporary_found_tv_item =
                            FoundItem(i.backdrop_path, i.name, i.id, i.popularity)
                        println("DOING SOME STUFF" + i.name)
                    } else {temporary_found_tv_item =
                        FoundItem(i.poster_path, i.name, i.id, i.popularity)}

                    temporalMutableList.add(temporary_found_tv_item)

                    general_list.value = temporalMutableList


                }



                Log.d(
                    ContentValues.TAG,
                    "LIST IS ${general_list.value}"
                );
            }else {
                throw RuntimeException("fetch failed")
            }
        }
    }



*/










}