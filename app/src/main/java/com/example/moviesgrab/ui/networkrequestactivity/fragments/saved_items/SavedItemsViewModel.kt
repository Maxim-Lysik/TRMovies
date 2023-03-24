package com.example.moviesgrab.ui.networkrequestactivity.fragments.saved_items

import androidx.lifecycle.*
import com.example.moviesgrab.MoviesRepository
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.FetchTrendingById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedItemsViewModel @Inject constructor(
    private val fetchTrendingById: FetchTrendingById,
    private val savedStateHandle: SavedStateHandle,
    val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


    fun saveItem(item: SavedItem) = viewModelScope.launch {
        moviesRepository.upsert(item)
    }

    fun getSavedItemsVM() = moviesRepository.getSavedItems()

    fun deleteSavedItemVM(savedItem: SavedItem) = viewModelScope.launch {
        moviesRepository.deleteSavedItem(savedItem)
    }



}