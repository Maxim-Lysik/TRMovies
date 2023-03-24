package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallovies

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesgrab.Constants
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentAfficheBinding
import com.example.moviesgrab.databinding.FragmentSeeAllMoviesfragmentBinding
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.ui.networkrequestactivity.NetworkRequestActivity
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.AfficheViewModel
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.SpacesItemDecoration
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.SpacesItemDecoration2
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.TrendingAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeeAllMoviesfragment : Fragment() {


    @Inject
    lateinit var fetchTrendingUseCase: FetchTrendingUseCase

    private var _binding: FragmentSeeAllMoviesfragmentBinding? = null

    lateinit var seeAllMoviesAdapter: SeeAllMoviesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val seeAllMoviesViewModel =
            ViewModelProvider(this).get(SeeAllMoviesViewModel::class.java)

        _binding = FragmentSeeAllMoviesfragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val array_movies_see_all = arrayListOf<ResultSingle>()
        //val seeAllRecyclerview = view?.findViewById<RecyclerView>(R.id.recycler_view_see_all)

        for (i in 1..10) {
            array_movies_see_all.add(Constants.item1_shm)
        }


        checkConnectivity()


       // array_movies_see_all.add(Constants.item1_shm)
       // array_movies_see_all.add(Constants.item1_shm)
       // array_movies_see_all.add(Constants.item1_shm)
       // array_movies_see_all.add(Constants.item1_shm)

        setupSeeAllMoviesRecyclerView(array_movies_see_all)

        binding.recyclerViewSeeAll.apply {
            adapter = seeAllMoviesAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            this.addItemDecoration(SpacesItemDecoration2(30))
        }

        /*seeAllRecyclerview.apply {
            seeAllRecyclerview?.adapter = seeAllMoviesAdapter
            seeAllRecyclerview?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
*/

        seeAllMoviesViewModel.result_items_movie_see_all.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                array_movies_see_all.removeAll(array_movies_see_all)
                array_movies_see_all.addAll(it)
                seeAllMoviesAdapter.bindData(array_movies_see_all)
            })



        return root
    }


    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }


    private fun setupSeeAllMoviesRecyclerView(seeAllList: ArrayList<ResultSingle>) {
        seeAllMoviesAdapter = SeeAllMoviesAdapter(this.requireActivity(), seeAllList)
        seeAllMoviesAdapter.bindData(seeAllList)
    }


    companion object {
    }

    private fun checkConnectivity() {
        val manager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        val seeAllMoviesViewModel =
            ViewModelProvider(this).get(SeeAllMoviesViewModel::class.java)


        if (null == activeNetwork) {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
           // val intent = Intent(this, NetworkRequestActivity::class.java)
            // set message of alert dialog
            dialogBuilder.setMessage("Make sure that WI-FI or mobile data is turned on, then try again")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Retry", DialogInterface.OnClickListener { dialog, id ->

                    seeAllMoviesViewModel.get_movies_result()
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


}