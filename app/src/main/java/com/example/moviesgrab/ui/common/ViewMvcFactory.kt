package com.example.moviesgrab.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.TrendingListViewMvc
import javax.inject.Inject
import javax.inject.Provider

class ViewMvcFactory @Inject constructor(
    private val layoutInflaterProvider: Provider<LayoutInflater>
   // private val imageLoaderProvider: Provider<ImageLoader>
) {

    fun newTrendingListViewMvc(parent: ViewGroup?): TrendingListViewMvc {
        return TrendingListViewMvc(layoutInflaterProvider.get(), parent)
    }

    /*fun newQuestionDetailsViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvc(layoutInflaterProvider.get(), imageLoaderProvider.get(), parent)
    }*/
}