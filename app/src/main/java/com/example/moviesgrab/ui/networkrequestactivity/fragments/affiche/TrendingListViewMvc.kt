package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesgrab.R
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.ui.common.BaseViewMvc

class TrendingListViewMvc (
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?
): BaseViewMvc<TrendingListViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.fragment_affiche
) {

    interface Listener {
       // fun onRefreshClicked()
        fun onItemClicked(clickedItem: ResultSingle)
      //  fun onViewModelClicked()
    }

  //  private val toolbar: MyToolbar
   // private val swipeRefresh: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    //private val questionsAdapter: TrendingItemsAdapter
    private val trendingAdapter: TrendingItemsAdapter

    init {

        /*toolbar = findViewById(R.id.toolbar)
        toolbar.setViewModelListener {
            for (listener in listeners) {
                listener.onViewModelClicked()
            }
        }*/

        /*// init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            for (listener in listeners) {
                listener.onRefreshClicked()
            }
        }
*/
        // init recycler view
        recyclerView = findViewById(R.id.trending_movies_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        trendingAdapter = TrendingItemsAdapter{ clickedItem ->
            for (listener in listeners) {
                listener.onItemClicked(clickedItem)
            }
        }
        recyclerView.adapter = trendingAdapter
    }

    fun bindTrending(items: List<ResultSingle>) {
        trendingAdapter.bindData(items)
    }

  /*  fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }*/

  /*  fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }*/

    class TrendingItemsAdapter(
        private val onTrendingItemClickListener: (ResultSingle) -> Unit
    ) : RecyclerView.Adapter<TrendingItemsAdapter.TrendingItemsViewHolder>() {

        private var trendinsList: List<ResultSingle> = ArrayList(0)

        inner class TrendingItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val trending_title: TextView = view.findViewById(R.id.see_all_movies_title)
            val trending_image: ImageView = view.findViewById(R.id.movies_image_see_all)
        }

        fun bindData(trendingItems: List<ResultSingle>) {
            trendinsList = ArrayList(trendingItems)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingItemsViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.treding_item, parent, false)
            return TrendingItemsViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TrendingItemsViewHolder, position: Int) {
            holder.trending_title.text = trendinsList[position].title
            holder.itemView.setOnClickListener {
                onTrendingItemClickListener.invoke(trendinsList[position])
            }
        }

        override fun getItemCount(): Int {
            return trendinsList.size
        }

    }
}
