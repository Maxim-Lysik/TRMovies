package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentDashboardBinding
import com.example.moviesgrab.networking.FetchSearchMoviesOrTvSeries
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.SpacesItemDecoration3
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies.SearchedResultMovie
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchingFragment : Fragment() {


    @Inject
    lateinit var fetchSearchMoviesOrTvSeries: FetchSearchMoviesOrTvSeries


    private var _binding: FragmentDashboardBinding? = null

    lateinit var searchingAdapter: SearchingAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var searchMoviesViewModel: SearchingViewModel

    var found_items: MutableList<ResultSingle> = mutableListOf<ResultSingle>()

    // var is_Loading = false

    val page = 1
    val limit = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchMoviesViewModel =
            ViewModelProvider(this).get(SearchingViewModel::class.java)



        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()

        setupRecyclerView()

        val name: String = "Ava"

        val sharedPreference =
            this.requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var needed = sharedPreference.getInt("Ass", 0)

        //val radioGroup = binding.radioGroup

        searchMoviesViewModel.temporalMutableList.clear()









        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            val searchMoviesViewModel =
                ViewModelProvider(this@SearchingFragment).get(SearchingViewModel::class.java)


            override fun onQueryTextSubmit(query: String?): Boolean {

                Log.d(ContentValues.TAG, "TESTING TEXT_SUBMIT &${query}")
                val name_to_put = sharedPreference.edit().putString("Point2", query).commit()
                searchMoviesViewModel.searchNewsPage = 1
                searchMoviesViewModel.searchItemsMovies(query.toString())


                /* if(searchingAdapter.differ.currentList.size == 0 ){
                     binding.noItemsPicture.visibility = View.VISIBLE
                 } else {binding.noItemsPicture.visibility = View.INVISIBLE}
 */


                // searchMoviesViewModel.temporalMutableList.clear()

                // searchMoviesViewModel.searchedMoviesResponse = null

                searchingAdapter.notifyDataSetChanged()


                /*searchMoviesViewModel.searchedMoviesResponse = null

                searchMoviesViewModel.searchNewsPage = 1
                Log.d(ContentValues.TAG, "TESTING TEXT_SUBMIT12")

               // searchMoviesViewModel.searchTVSeries.value.

                searchingAdapter.notifyDataSetChanged()
                   // searchMoviesViewModel.temporalMutableList.clear()


                    Log.d(ContentValues.TAG, "CLICKED ON TV SERIES")

                   // searchMoviesViewModel.searchItemsMovies(query.toString())
                    searchMoviesViewModel.searchItemsMovies(query.toString())




               // var name2 = sharedPreference.getString("Point2", "0")


                    searchingAdapter.notifyDataSetChanged()

                    setupRecycler(query.toString())
                    searchingAdapter.notifyDataSetChanged()

*/


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Log.d(ContentValues.TAG, "TESTING TEXT_CHANGED")

                Log.d(ContentValues.TAG, "TESTING TEXT_CHANGED &${newText}")
                //searchMoviesViewModel.searchNewsPage = 1
                searchMoviesViewModel.temporalMutableList.clear()

                searchMoviesViewModel.searchNewsPage = 1

                searchMoviesViewModel.searchedMoviesResponse = null
                // searchMoviesViewModel.searchItemsMovies(newText.toString())
                searchingAdapter.notifyDataSetChanged()

                return false
            }
        })


        searchMoviesViewModel.searchTVSeries.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {


                var parameterList = it.data?.results?.dropWhile { it.backdrop_path != null }
                    ?.distinctBy { it.title }
                var parameterList2 = it.data?.results?.takeWhile { it.backdrop_path != null }
                    ?.distinctBy { it.title }

                searchingAdapter.differ.submitList(parameterList2)

                //searchingAdapter.differ.submitList(it.data?.results?.distinctBy { it.title })
                var name2 = sharedPreference.getString("Point2", "0")
                searchingAdapter.notifyDataSetChanged()



                if (parameterList2!!.size == 0 || parameterList2 == null) {
                    binding.noItemsPicture.visibility = View.VISIBLE
                    binding.nothingFound.visibility = View.VISIBLE
                } else {
                    binding.noItemsPicture.visibility = View.INVISIBLE
                    binding.nothingFound.visibility = View.INVISIBLE
                }


                /*     if(searchingAdapter.differ.currentList.size == 0 ){
                         binding.noItemsPicture.visibility = View.VISIBLE
                     } else {binding.noItemsPicture.visibility = View.INVISIBLE}
         */
            })






        return root
    }


    var page2 = 2

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            val searchMoviesViewModel =
                ViewModelProvider(this@SearchingFragment).get(SearchingViewModel::class.java)


            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            val visibleItemCount: Int = layoutManager.childCount
            val pastVisibleItem: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
            val total = searchingAdapter.itemCount


            //  if(!searchMoviesViewModel.is_Loading) {
            if (visibleItemCount + pastVisibleItem >= total) {
                searchMoviesViewModel.searchItemsMovies(binding.searchView.query.toString())

                searchingAdapter.notifyDataSetChanged()
                ++page2
            } else {
                Log.d("PIZDA", "PIZDA")

            }
            //  }

            //recyclerView.scrollToPosition(visibleItemCount-1)

            Log.d("Scroll", "VISIBLE $visibleItemCount")
            Log.d("Scroll", "PAST $pastVisibleItem")
            Log.d("Scroll", "TOTAL $total")

            super.onScrolled(recyclerView, dx, dy)
        }
    }


    private fun checkConnectivity() {
        val manager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        if (null == activeNetwork) {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            // val intent = Intent(this, NetworkRequestActivity::class.java)
            // set message of alert dialog
            dialogBuilder.setMessage("Make sure that WI-FI or mobile data is turned on, then try again")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Retry", DialogInterface.OnClickListener { dialog, id ->
                    requireActivity().recreate()
                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    requireActivity().finish()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("No Internet Connection")
            alert.setIcon(R.mipmap.ic_launcher)
            // show alert dialog
            alert.show()
        }
    }


    fun getPage() {


    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    /*val scrollListener = object : RecyclerView.OnScrollListener() {

        val searchMoviesViewModel =
            ViewModelProvider(this@SearchingFragment).get(SearchingViewModel::class.java)

        val pages_number: Int = searchMoviesViewModel.pages_number_tv_series



         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
             super.onScrollStateChanged(recyclerView, newState)
             if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                 isScrolling = true
             }
         }

         override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
             super.onScrolled(recyclerView, dx, dy)

             val layoutManager = recyclerView.layoutManager as LinearLayoutManager
             val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
             val visibleItemCount = layoutManager.childCount
             val totalItemCount = layoutManager.itemCount
             val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
             val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
             val isNotAtBeginning = firstVisibleItemPosition >= 0
             val isTotalMoreThanVisible = totalItemCount >= 4
             var page_number = viewModel.breakingNewsPage
             val pages_not_more_than_two = page_number <= 1
             val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                     isTotalMoreThanVisible && isScrolling && pages_not_more_than_two

             val API_KEY: String = BuildConfig.API_KEY


             if (shouldPaginate) {
                 viewModel.getBreakingNews(
                     "skateboarding",
                     viewModel.breakingNewsPage,
                     "en",
                     "free-news.p.rapidapi.com",
                     API_KEY
                 )
                 isScrolling = false
             }

         }

     }
 */


    private fun setupRecyclerView() {
        searchingAdapter = SearchingAdapter(this.requireActivity())
        binding.recyclerFoundItems.apply {
            adapter = searchingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            this.addItemDecoration(SpacesItemDecoration3(30))
            addOnScrollListener(this@SearchingFragment.scrollListener)
        }
    }


    /*/


     private fun setupFoundItemsRecyclerView(newList: MutableList<SearchedTVSeries>) {
        searchingAdapter = SearchingAdapter(this.requireActivity(), newList)
        searchingAdapter.bindData(newList)
    }

     */


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}