package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.webviewmovies

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentWebMoviesBinding
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.FetchTrendingById
import com.example.moviesgrab.networking.ResultSingle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WebFragmentMovies : Fragment() {

    @Inject
    lateinit var fetchTrendingByID: FetchTrendingById

    lateinit var webFragmentViewModelMovies: WebFragmentViewModelMovies

    lateinit var progress_bar: ProgressBar

    private var _binding: FragmentWebMoviesBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWebMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()

        webFragmentViewModelMovies =
            ViewModelProvider(this)[WebFragmentViewModelMovies::class.java]

        val webView = binding.webViewMovies

        progress_bar = binding.progressBarMovies
        progress_bar.max = 100
        webView.visibility = View.INVISIBLE
        progress_bar.visibility = View.VISIBLE


        val bundle = this.arguments
        if (bundle != null) {
            val myInt = bundle.getSerializable("our_obj") as ResultSingle
            val dick = myInt.poster_path
            val id = myInt.id.toString()

            val check = "fdsf"

            //try {
            webFragmentViewModelMovies.pushMovie(id)
            webFragmentViewModelMovies.pushMovieToSaved(id)
            /*  } catch (e: Exception) {
                  null
              }*/


        }

        webFragmentViewModelMovies.result_movie_item_by_id.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Log.d(ContentValues.TAG, "SHOWING HOMEPAGE22222 ${it.homepage}")
                webViewSetup(it.homepage.toString())
            })


        binding.floatingButtonSavingMovie.setOnClickListener {

            webFragmentViewModelMovies.result_movie_item_by_id_for_saving.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {

                    Log.d(ContentValues.TAG, "SHOWING ITEM TO BE SAVED ${it.title} + ${it.homepage.toString()} + ${it.popularity}  +  ${it.backdrop_path} +  ${it.overview}")
                    val item_to_save_movies: SavedItem = SavedItem(it.title.toString(),
                        it.homepage.toString(), it.popularity!!, it.poster_path.toString()!!
                    )
                    webFragmentViewModelMovies.saveArticle(item_to_save_movies)
                })

            it.setClickable(false);


         /*   webFragmentViewModelMovies.result_movie_item_by_id_for_saving.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    // val item_to_save_movies: SavedItem = SavedItem(it., it.homepage, it.popularity)
                    //webFragmentViewModelMovies.saveArticle(item_to_save_movies)
                })
*/   // КОД ЗДКСЬ ДОБАВИТЬ

        }


     /*   webFragmentViewModelMovies.result_movie_item_by_id_for_saving.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                Log.d(ContentValues.TAG, "SHOWING ITEM TO BE SAVED ${it.title} + ${it.homepage} + ${it.popularity}  ")
                val item_to_save_movies: SavedItem = SavedItem(it.title.toString(),
                    it.homepage.toString(), it.popularity!!
                )
                webFragmentViewModelMovies.saveArticle(item_to_save_movies)
            })

*/


        // Inflate the layout for this fragment
        return root
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }


    @SuppressLint("NewApi")
    fun webViewSetup(url: String) {

        progress_bar = binding.progressBarMovies
        progress_bar.max = 100

        binding.webViewMovies.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility = View.INVISIBLE
                progress_bar.visibility = View.VISIBLE
            }

            /* override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                 val overrideUrlLoading = false
                 view.loadUrl(url!!)
                 progress_bar.setVisibility(View.VISIBLE)
                 return true
             }*/

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.visibility = View.VISIBLE
                progress_bar.visibility = View.INVISIBLE
            }

        }

        binding.webViewMovies.apply {

            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            //settings.blockNetworkLoads = true
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            binding.webViewMovies.setScrollbarFadingEnabled(false)
            settings.javaScriptCanOpenWindowsAutomatically = true

            val newUA =
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50"

            binding.webViewMovies.getSettings().setUserAgentString(newUA)


            if (18 < Build.VERSION.SDK_INT) {
                //18 = JellyBean MR2, KITKAT=19
                binding.webViewMovies.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            }

            if (Build.VERSION.SDK_INT >= 19) {
                binding.webViewMovies.getSettings()
                    .setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }


            loadUrl(url)
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


}