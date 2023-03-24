package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.personfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.moviesgrab.networking.FetchPopularPersons
import com.example.moviesgrab.networking.FetchTrendingById
import com.example.moviesgrab.networking.TvSeriesSingle
import com.example.moviesgrab.networking.person.SinglePerson
import javax.inject.Inject

class PersonOneViewModel @Inject constructor(
    private val fetchTrendingById: FetchTrendingById,
    private val fetchPopularPersons: FetchPopularPersons,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _result_single_person: MutableLiveData<SinglePerson> = savedStateHandle.getLiveData("questions")
    val result_single_person: LiveData<SinglePerson> get() = _result_single_person





}