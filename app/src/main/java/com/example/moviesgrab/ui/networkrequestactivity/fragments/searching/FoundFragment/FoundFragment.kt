package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.FoundFragment

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentFoundBinding
import com.example.moviesgrab.networking.ResultSingle
import java.math.RoundingMode
import java.text.DecimalFormat


class FoundFragment : Fragment() {


    lateinit var foundFragmentViewModel: FoundFragmentViewModel
    private var _binding: FragmentFoundBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFoundBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()



        foundFragmentViewModel =
            ViewModelProvider(this)[FoundFragmentViewModel::class.java]


        val bundle = this.arguments
        if (bundle != null) {
            val myInt = bundle.getSerializable("our_obj2") as ResultSingle
            val dick = myInt.poster_path
            val id = myInt.id.toString()

            val check = "fdsf"

            foundFragmentViewModel.our_found_item.value = myInt


            //try {
            // webFragmentViewModelMovies.pushMovie(id)
            //  webFragmentViewModelMovies.pushMovieToSaved(id)
            /*  } catch (e: Exception) {
                  null
              }*/


        }

        foundFragmentViewModel.our_found_item.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                binding.textik.text = it.title
                binding.description.text = it.overview
              //  binding.testing.text = it.popularity.toString()
                binding.releaseDate.text = it.release_date


                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.UP
                var rating = it.vote_average
                val roundoff_rating = df.format(rating)



                binding.rating.text = roundoff_rating.toString()

                if (it.backdrop_path != null) {
                    setupImage(it.backdrop_path)
                } else {setupImage(it.poster_path.toString())}



            })


        binding.backButton.setOnClickListener {

            findNavController().popBackStack()

        }





        return root


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
        drawable.strokeWidth = 15f
        drawable.centerRadius = 70f
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