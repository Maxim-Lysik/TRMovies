package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesgrab.networking.FetchPopularPersons
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.networking.person.SinglePerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AfficheViewModel @Inject constructor(
    private val fetchTrendingUseCase: FetchTrendingUseCase,
    private val fetchPopularPersons: FetchPopularPersons,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {


    private var _result_items_movie: MutableLiveData<List<ResultSingle>> = savedStateHandle.getLiveData("questions")
    val result_items_movie: LiveData<List<ResultSingle>> get() = _result_items_movie

    private var _result_items_tv: MutableLiveData<List<ResultSingle>> = savedStateHandle.getLiveData("questions2")
    val result_items_tv: LiveData<List<ResultSingle>> get() = _result_items_tv

    private var _result_popular_persons: MutableLiveData<List<SinglePerson>> = savedStateHandle.getLiveData<List<SinglePerson>>("questions3")
    val result_popular_persons: LiveData<List<SinglePerson>> get() = _result_popular_persons





    init {
        viewModelScope.launch {
            delay(1000)
            val result_movie = fetchTrendingUseCase.fetch_treding_movies()
            if (result_movie is FetchTrendingUseCase.Result.Success) {
                _result_items_movie.value = result_movie.items.results  // прописать для списка

                Log.d(ContentValues.TAG, "Testing items ${_result_items_movie.value}");
            } else {
                throw RuntimeException("fetch failed")
            }
        }


        viewModelScope.launch {
            delay(1000)
            val result_tv = fetchTrendingUseCase.fetch_treding_tv()
            if (result_tv is FetchTrendingUseCase.Result.Success) {
                _result_items_tv.value = result_tv.items.results  // прописать для списка

                Log.d(ContentValues.TAG, "Testing items2 ${_result_items_tv.value}");
            } else {
                throw RuntimeException("fetch failed")
            }
        }

        viewModelScope.launch {
            delay(1000)
            val result_persons = fetchPopularPersons.fetch_popular_persons()
            if (result_persons is FetchPopularPersons.Result.Success) {
                _result_popular_persons.value = result_persons.items.results// прописать для списка

                Log.d(ContentValues.TAG, "TESTING PERSONS FOR REAL ${_result_popular_persons.value}");

                for (i in result_persons.items.results) { println("NAME OF PERSON "  + i.name + " PICTURE PATH" + i.profile_path ) }
            } else {
                throw RuntimeException("fetch failed")
            }
        }



    }




// test12


    private val _text = MutableLiveData<String>().apply {
       // value = "This is home Fragment"
    }
    val text: LiveData<String> = _text






}
