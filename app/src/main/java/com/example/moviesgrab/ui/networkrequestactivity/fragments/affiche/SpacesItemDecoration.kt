package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State


class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: State
    ) {
       // outRect.left = space
        outRect.right = space
       // outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = space
        } else {
            outRect.top = 0
        }
    }
}


class SpacesItemDecoration2(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: State
    ) {
        // outRect.left = space
      //  outRect.top = space
        // outRect.bottom = space
        //if (parent.getChildLayoutPosition(view) == 0) {  outRect.top = space}
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
            outRect.bottom = space
        } else {
            outRect.bottom = space
            //outRect.left = 0
        }
    }
}


class SpacesItemDecoration3(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: State
    ) {
        // outRect.left = space
        //  outRect.top = space
        // outRect.bottom = space
        //if (parent.getChildLayoutPosition(view) == 0) {  outRect.top = space}
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space + space
            outRect.bottom = space
        } else {
            outRect.bottom = space
            //outRect.left = 0
        }
    }
}