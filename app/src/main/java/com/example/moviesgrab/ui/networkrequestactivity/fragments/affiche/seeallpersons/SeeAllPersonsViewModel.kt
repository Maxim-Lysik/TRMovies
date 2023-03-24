package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallpersons

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
class SeeAllPersonsViewModel @Inject constructor(
    private val fetchTrendingUseCase: FetchTrendingUseCase,
    private val fetchPopularPersons: FetchPopularPersons,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {


    private var _result_items_persons_see_all: MutableLiveData<List<SinglePerson>> = savedStateHandle.getLiveData("questions")
    val result_items_persons_see_all: LiveData<List<SinglePerson>> get() = _result_items_persons_see_all




    init {

        viewModelScope.launch {
            delay(1000)
            val result_persons = fetchPopularPersons.fetch_popular_persons()
            if (result_persons is FetchPopularPersons.Result.Success) {
                _result_items_persons_see_all.value = result_persons.items.results// прописать для списка

                Log.d(
                    ContentValues.TAG,
                    "TESTING PERSONS FOR REAL ${_result_items_persons_see_all.value}"
                );

                for (i in result_persons.items.results) {
                    println("NAME OF PERSON " + i.name + " PICTURE PATH" + i.profile_path)
                }
            } else {
              //  throw RuntimeException("fetch failed")
            }
        }
    }

    fun get_persons_result(){
        viewModelScope.launch {
            delay(1000)
            val result_persons = fetchPopularPersons.fetch_popular_persons()
            if (result_persons is FetchPopularPersons.Result.Success) {
                _result_items_persons_see_all.value = result_persons.items.results// прописать для списка

                Log.d(
                    ContentValues.TAG,
                    "TESTING PERSONS FOR REAL ${_result_items_persons_see_all.value}"
                );

                for (i in result_persons.items.results) {
                    println("NAME OF PERSON " + i.name + " PICTURE PATH" + i.profile_path)
                }
            } else {
                //  throw RuntimeException("fetch failed")
            }
        }
    }






}