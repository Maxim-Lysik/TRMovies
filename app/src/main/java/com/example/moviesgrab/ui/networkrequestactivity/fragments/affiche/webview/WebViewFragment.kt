package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.webview

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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentWebViewBinding
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.FetchTrendingById
import com.example.moviesgrab.networking.ResultSingle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WebViewFragment : Fragment() {

    @Inject
    lateinit var fetchTrendingByID: FetchTrendingById

    lateinit var webFragmentViewModel: WebFragmentViewModel

    lateinit var progress_bar: ProgressBar

    private var _binding: FragmentWebViewBinding? = null

    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()

        webFragmentViewModel =
            ViewModelProvider(this)[WebFragmentViewModel::class.java]


        val webView = binding.webViewTVSeries

        progress_bar = binding.progressBar
        progress_bar.max = 100
        webView.visibility = View.INVISIBLE
        progress_bar.visibility = View.VISIBLE


        val bundle = this.arguments
        if (bundle != null) {
            val myInt = bundle.getSerializable("our_obj") as ResultSingle
            val dick = myInt.poster_path
            val id = myInt.id.toString()

            val check = "fdsf"



            webFragmentViewModel.pushTV(id)

        }


        binding.floatingButtonSaving.setOnClickListener {
            webFragmentViewModel.result_tv_item_by_id.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    val item_to_save:SavedItem = SavedItem(it.name, it.homepage, it.popularity, it.poster_path)
                    webFragmentViewModel.saveArticle(item_to_save)
                })

        }






        webFragmentViewModel.result_tv_item_by_id.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Log.d(ContentValues.TAG, "SHOWING HOMEPAGE2 ${it.homepage}")

                //val item_to_save:SavedItem = SavedItem(it.homepage, it.name, it.popularity)
              //  webFragmentViewModel.saveArticle(item_to_save)
                webViewSetup(it.homepage)
            })


        // Inflate the layout for this fragment
        return root
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }


    @SuppressLint("NewApi")
    fun webViewSetup(url: String) {

        progress_bar = binding.progressBar
        progress_bar.max = 100

        binding.webViewTVSeries.webViewClient = object : WebViewClient() {

           override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility =View.INVISIBLE
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
                view.visibility =View.VISIBLE
                progress_bar.visibility = View.INVISIBLE
            }

        }

        binding.webViewTVSeries.apply {

            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            //settings.blockNetworkLoads = true
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            binding.webViewTVSeries.setScrollbarFadingEnabled(false)
            settings.javaScriptCanOpenWindowsAutomatically = true

            val newUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50"

            binding.webViewTVSeries.getSettings().setUserAgentString(newUA)


            if (18 < Build.VERSION.SDK_INT ){
                //18 = JellyBean MR2, KITKAT=19
                binding.webViewTVSeries.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            }

            if (Build.VERSION.SDK_INT >= 19) {
                binding.webViewTVSeries.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }



            loadUrl(url)
        }

    }


    private fun checkConnectivity() {
        val manager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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