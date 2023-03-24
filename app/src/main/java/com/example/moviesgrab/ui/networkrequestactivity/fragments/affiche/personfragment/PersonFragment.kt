package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.personfragment

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentPersonBinding
import com.example.moviesgrab.networking.FetchPopularPersons
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.person.KnownFor
import com.example.moviesgrab.networking.person.SinglePerson
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.SpacesItemDecoration2
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment : Fragment() {


    @Inject
    lateinit var fetchTrendingUseCase: FetchTrendingUseCase


    lateinit var fetchPopularPersons: FetchPopularPersons

    private var _binding: FragmentPersonBinding? = null


    lateinit var personOneAdapter: PersonOneAdapter

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()

        val array_known_for = arrayListOf<KnownFor>()
        //setupPersonFragmentRecyclerView()


        val bundle = this.arguments
        if (bundle != null) {
            val myInt = bundle.getSerializable("our_person") as SinglePerson
            val dick = myInt.name
            val id = myInt.id.toString()
            val image_url = myInt.profile_path

            val known_for1: List<KnownFor> = myInt.known_for!!

          // val checking = myInt.

            array_known_for.addAll(known_for1)
            setupPersonFragmentRecyclerView(array_known_for)






            binding.knownForRecycler.apply {

                adapter = personOneAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                this.addItemDecoration(SpacesItemDecoration2(30))
            }
            /*binding.apply {
                adapter = seeAllPersonsAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }*/

            binding.actorsName.text = dick
            setupImage(image_url.toString())
            Log.d(ContentValues.TAG, "COUGHT NAME $dick")
            Log.d(ContentValues.TAG, "COUGHT KNOWN FOR $known_for1")


        }


        binding.backButtonPerson.setOnClickListener{
            findNavController().popBackStack()
        }


        // Inflate the layout for this fragment
        return root
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }


    fun setupImage(url: String) {

        // var actor_image_url = "https://image.tmdb.org/t/p/original" + trendingAllPersonsList[position].profile_path
        val drawable = CircularProgressDrawable(requireActivity())
        drawable.setColorSchemeColors(
            ContextCompat.getColor(
                requireActivity(),
                R.color.white
            )
        )  // Can change color here
        drawable.strokeWidth = 10f
        drawable.centerRadius = 60f
        drawable.start()


        var actor_image_url = "https://image.tmdb.org/t/p/original" + url
        Glide.with(this)
            .load(actor_image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .centerCrop()
            .placeholder(drawable)
            //.fitCenter()
            .into(binding.actorsImageview);

    }


    private fun setupPersonFragmentRecyclerView(knownForList: ArrayList<KnownFor>) {
        personOneAdapter = PersonOneAdapter(this.requireActivity(), knownForList)
        personOneAdapter.bindData(knownForList)
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


}