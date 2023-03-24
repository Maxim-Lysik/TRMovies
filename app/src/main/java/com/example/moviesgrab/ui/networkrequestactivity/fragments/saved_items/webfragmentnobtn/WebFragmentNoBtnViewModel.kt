package com.example.moviesgrab.ui.networkrequestactivity.fragments.saved_items.webfragmentnobtn

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesgrab.MoviesRepository
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.FetchTrendingById
import com.example.moviesgrab.networking.TvSeriesSingle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WebFragmentNoBtnViewModel @Inject constructor(
    private val fetchTrendingById: FetchTrendingById,
    private val savedStateHandle: SavedStateHandle,
    val moviesRepository: MoviesRepository
) : ViewModel() {

    var _result_saved_item: MutableLiveData<SavedItem> = savedStateHandle.getLiveData("our_obj_saved")
    val result_saved_item: LiveData<SavedItem> get() = _result_saved_item


}