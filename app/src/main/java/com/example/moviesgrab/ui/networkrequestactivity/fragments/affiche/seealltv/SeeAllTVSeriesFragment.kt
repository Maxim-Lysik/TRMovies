package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seealltv

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesgrab.Constants
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentSeeAllMoviesfragmentBinding
import com.example.moviesgrab.databinding.FragmentSeeAllTvSeriesBinding
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.SpacesItemDecoration2
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallovies.SeeAllMoviesAdapter
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallovies.SeeAllMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SeeAllTVSeriesFragment : Fragment() {

    @Inject
    lateinit var fetchTrendingUseCase: FetchTrendingUseCase

    private var _binding: FragmentSeeAllTvSeriesBinding? = null

    lateinit var seeAllTVSeriesAdapter: SeeAllTVSeriesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val seeAllTVSeriesViewModel =
            ViewModelProvider(this).get(SeeAllTVSeriesViewModel::class.java)

        _binding = FragmentSeeAllTvSeriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()

        val array_tv_series_see_all = arrayListOf<ResultSingle>()

        for (i in 1..10) {
            array_tv_series_see_all.add(Constants.item1_shm)
        }

        setupSeeAllTVSeriesRecyclerView(array_tv_series_see_all)

        binding.recyclerViewTVSeries.apply {
            adapter = seeAllTVSeriesAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            this.addItemDecoration(SpacesItemDecoration2(30))
        }


        seeAllTVSeriesViewModel.result_items_tvseries_see_all.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                array_tv_series_see_all.removeAll(array_tv_series_see_all)
                array_tv_series_see_all.addAll(it)
                seeAllTVSeriesAdapter.bindData(array_tv_series_see_all)
            })


        // Inflate the layout for this fragment
        return root
    }


    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }


    private fun checkConnectivity() {
        val manager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        val seeAllTVSeriesViewModel =
            ViewModelProvider(this).get(SeeAllTVSeriesViewModel::class.java)

        if (null == activeNetwork) {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            // val intent = Intent(this, NetworkRequestActivity::class.java)
            // set message of alert dialog
            dialogBuilder.setMessage("Make sure that WI-FI or mobile data is turned on, then try again")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Retry", DialogInterface.OnClickListener { dialog, id ->
                    seeAllTVSeriesViewModel.get_tvseries_result()
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


    private fun setupSeeAllTVSeriesRecyclerView(seeAllTVSeriesList: ArrayList<ResultSingle>) {
        seeAllTVSeriesAdapter = SeeAllTVSeriesAdapter(this.requireActivity(), seeAllTVSeriesList)
        seeAllTVSeriesAdapter.bindData(seeAllTVSeriesList)
    }


}