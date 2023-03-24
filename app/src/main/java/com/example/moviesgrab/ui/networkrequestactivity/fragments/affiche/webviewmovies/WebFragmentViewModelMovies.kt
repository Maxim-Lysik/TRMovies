package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.webviewmovies

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesgrab.MoviesRepository
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.FetchTrendingById
import com.example.moviesgrab.networking.MovieResultSingle
import com.example.moviesgrab.networking.TvSeriesSingle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WebFragmentViewModelMovies @Inject constructor(
    private val fetchTrendingById: FetchTrendingById,
    private val savedStateHandle: SavedStateHandle,
    val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _result_movie_item_by_id: MutableLiveData<TvSeriesSingle> = savedStateHandle.getLiveData("questions")
    val result_movie_item_by_id: LiveData<TvSeriesSingle> get() = _result_movie_item_by_id


    // FOR SAVING DATA

    private var _result_movie_item_by_id_for_saving: MutableLiveData<MovieResultSingle> = savedStateHandle.getLiveData("questions3")
    val result_movie_item_by_id_for_saving: LiveData<MovieResultSingle> get() = _result_movie_item_by_id_for_saving
 //

    fun pushMovie(id: String) = viewModelScope.launch {
        // delay(100)
        val result_movie_by_id = fetchTrendingById.fetch_treding_movie_by_id(id)
        if (result_movie_by_id is FetchTrendingById.Result.Success) {
            _result_movie_item_by_id.value = result_movie_by_id.item  // прописать для списка

            Log.d(ContentValues.TAG, "TESTING MOVIE ITEM ${_result_movie_item_by_id.value}");
        } else {
            //throw RuntimeException("fetch failed")
        }
    }



    fun pushMovieToSaved(id: String) = viewModelScope.launch {
        // delay(100)
        val result_movie_by_id_saving = fetchTrendingById.fetch_treding_movie_by_id_for_saving(id)
        if (result_movie_by_id_saving is FetchTrendingById.Result.SuccessMovie) {
            _result_movie_item_by_id_for_saving.value = result_movie_by_id_saving.item  // прописать для списка

            Log.d(ContentValues.TAG, "TESTING MOVIE ITEM FOR SAVING ${_result_movie_item_by_id_for_saving.value}");
        } else {
            //throw RuntimeException("fetch failed")
        }
    }






    fun saveArticle(saved_article: SavedItem) = viewModelScope.launch {
        moviesRepository.upsert(saved_article)
    }


}