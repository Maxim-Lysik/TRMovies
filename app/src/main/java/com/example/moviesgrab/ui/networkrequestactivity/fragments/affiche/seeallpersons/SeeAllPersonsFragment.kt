package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallpersons

import android.R
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesgrab.Constants
import com.example.moviesgrab.databinding.FragmentSeeAllPersonsBinding
import com.example.moviesgrab.networking.FetchPopularPersons
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.person.SinglePerson
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.SpacesItemDecoration2
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SeeAllPersonsFragment : Fragment() {


    @Inject
    lateinit var fetchTrendingUseCase: FetchTrendingUseCase

    @Inject
    lateinit var fetchPopularPersons: FetchPopularPersons

    private var _binding: FragmentSeeAllPersonsBinding? = null

    lateinit var seeAllPersonsAdapter: SeeAllPersonsAdapter

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        checkConnectivity()

        val seeAllPersonsViewModel =
            ViewModelProvider(this).get(SeeAllPersonsViewModel::class.java)

        _binding = FragmentSeeAllPersonsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val array_see_all_persons = arrayListOf<SinglePerson>()

        for (i in 1..10) {
            array_see_all_persons.add(Constants.person1)
        }


        setupSeeAllpersonsRecyclerView(array_see_all_persons)

        binding.recyclerViewSeeAllPersons.apply {
            adapter = seeAllPersonsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            this.addItemDecoration(SpacesItemDecoration2(30))
        }


        /*  seeAllMoviesViewModel.result_items_movie_see_all.observe(
              viewLifecycleOwner,
              androidx.lifecycle.Observer {
                  array_movies_see_all.removeAll(array_movies_see_all)
                  array_movies_see_all.addAll(it)
                  seeAllMoviesAdapter.bindData(array_movies_see_all)
              })*/

        seeAllPersonsViewModel.result_items_persons_see_all.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                array_see_all_persons.removeAll(array_see_all_persons)
                array_see_all_persons.addAll(it)
                seeAllPersonsAdapter.bindData(array_see_all_persons)
            })


        // Inflate the layout for this fragment
        return root
    }


    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }

    override fun onPause() {
        super.onPause()
        checkConnectivity()
    }


    private fun setupSeeAllpersonsRecyclerView(seeAllPersonsList: ArrayList<SinglePerson>) {
        seeAllPersonsAdapter = SeeAllPersonsAdapter(this.requireActivity(), seeAllPersonsList)
        seeAllPersonsAdapter.bindData(seeAllPersonsList)
    }


    private fun checkConnectivity() {
        val manager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        val seeAllPersonsViewModel =
            ViewModelProvider(this).get(SeeAllPersonsViewModel::class.java)

        if (null == activeNetwork) {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            // val intent = Intent(this, NetworkRequestActivity::class.java)
            // set message of alert dialog
            dialogBuilder.setMessage("Make sure that WI-FI or mobile data is turned on, then try again")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Retry", DialogInterface.OnClickListener { dialog, id ->

                    seeAllPersonsViewModel.get_persons_result()
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
            alert.setIcon(com.example.moviesgrab.R.mipmap.ic_launcher)
            // show alert dialog
            alert.show()
        }
    }


}