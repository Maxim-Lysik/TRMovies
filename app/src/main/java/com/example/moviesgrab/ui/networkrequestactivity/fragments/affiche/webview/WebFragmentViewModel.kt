package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.webview

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesgrab.MoviesRepository
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.FetchTrendingById
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.networking.TvSeriesSingle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebFragmentViewModel @Inject constructor(
    private val fetchTrendingById: FetchTrendingById,
    private val savedStateHandle: SavedStateHandle,
    val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _result_tv_item_by_id: MutableLiveData<TvSeriesSingle> = savedStateHandle.getLiveData("questions")
    val result_tv_item_by_id: LiveData<TvSeriesSingle> get() = _result_tv_item_by_id



    fun pushTV(id: String) = viewModelScope.launch {
       // delay(100)
        val result_tv_series_by_id = fetchTrendingById.fetch_treding_tv_by_id(id)
        if (result_tv_series_by_id is FetchTrendingById.Result.Success) {
            _result_tv_item_by_id.value = result_tv_series_by_id.item  // прописать для списка

            Log.d(ContentValues.TAG, "Testing items2 ${_result_tv_item_by_id.value}");
        } else {
           // throw RuntimeException("fetch failed")
        }
    }

    fun saveArticle(saved_article: SavedItem) = viewModelScope.launch {
        moviesRepository.upsert(saved_article)
    }




}