package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.FoundFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesgrab.networking.ResultSingle

class FoundFragmentViewModel : ViewModel() {


    val our_found_item: MutableLiveData<ResultSingle> = MutableLiveData<ResultSingle>()


}