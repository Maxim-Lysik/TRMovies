package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallovies

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.ResultSingle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SeeAllMoviesViewModel @Inject constructor(
    private val fetchTrendingUseCase: FetchTrendingUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {


    private var _result_items_movie_see_all: MutableLiveData<List<ResultSingle>> = savedStateHandle.getLiveData("questions")
    val result_items_movie_see_all: LiveData<List<ResultSingle>> get() = _result_items_movie_see_all


    init{
        viewModelScope.launch {
            delay(1000)
            val result_movie = fetchTrendingUseCase.fetch_treding_movies()
            if (result_movie is FetchTrendingUseCase.Result.Success) {
                _result_items_movie_see_all.value = result_movie.items.results  // прописать для списка

                Log.d(ContentValues.TAG, "Testing items See all ${_result_items_movie_see_all.value}");
            } else {
             //   throw RuntimeException("fetch failed")
            }
        }
    }

    fun get_movies_result(){
        viewModelScope.launch {
            delay(1000)
            val result_movie = fetchTrendingUseCase.fetch_treding_movies()
            if (result_movie is FetchTrendingUseCase.Result.Success) {
                _result_items_movie_see_all.value = result_movie.items.results  // прописать для списка

                Log.d(ContentValues.TAG, "Testing items See all ${_result_items_movie_see_all.value}");
            } else {
                //   throw RuntimeException("fetch failed")
            }
        }
    }



}