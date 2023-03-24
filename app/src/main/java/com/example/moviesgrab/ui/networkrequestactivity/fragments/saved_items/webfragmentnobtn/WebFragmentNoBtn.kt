package com.example.moviesgrab.ui.networkrequestactivity.fragments.saved_items.webfragmentnobtn

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentWebNoBtnBinding
import com.example.moviesgrab.databinding.FragmentWebViewBinding
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.webview.WebFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebFragmentNoBtn : Fragment() {


    private var _binding: FragmentWebNoBtnBinding? = null

    lateinit var webFragmentNoBtnVm: WebFragmentNoBtnViewModel

    private val binding get() = _binding!!

    lateinit var progress_bar_no_btn: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentWebNoBtnBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()


        webFragmentNoBtnVm =
            ViewModelProvider(this)[WebFragmentNoBtnViewModel::class.java]


        val webView = binding.webViewTVSeriesNoBtn

        progress_bar_no_btn = binding.progressBarNoBtn
        progress_bar_no_btn.max = 100
        webView.visibility = View.INVISIBLE
        progress_bar_no_btn.visibility = View.VISIBLE



        val bundle = this.arguments
        if (bundle != null) {
            val myInt = bundle.getSerializable("our_obj_saved") as SavedItem
            val dick = myInt.article_link
            val id = myInt.id.toString()

            webFragmentNoBtnVm._result_saved_item.value = bundle.getSerializable("our_obj_saved") as SavedItem

        }




        webFragmentNoBtnVm._result_saved_item.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Log.d(ContentValues.TAG, "ARTICLE LINK ${it.article_link}")

                webViewSetup(it.article_link)
                // HERE
              //  webViewSetup(it.homepage)
            })





        // Inflate the layout for this fragment
        return root
    }






    @SuppressLint("NewApi")
    fun webViewSetup(url: String) {

        progress_bar_no_btn = binding.progressBarNoBtn
        progress_bar_no_btn.max = 100

        binding.webViewTVSeriesNoBtn.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility =View.INVISIBLE
                progress_bar_no_btn.visibility = View.VISIBLE
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
                progress_bar_no_btn.visibility = View.INVISIBLE
            }

        }

        binding.webViewTVSeriesNoBtn.apply {

            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            //settings.blockNetworkLoads = true
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            binding.webViewTVSeriesNoBtn.setScrollbarFadingEnabled(false)
            settings.javaScriptCanOpenWindowsAutomatically = true

            val newUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50"

            binding.webViewTVSeriesNoBtn.getSettings().setUserAgentString(newUA)


            if (18 < Build.VERSION.SDK_INT ){
                //18 = JellyBean MR2, KITKAT=19
                binding.webViewTVSeriesNoBtn.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            }

            if (Build.VERSION.SDK_INT >= 19) {
                binding.webViewTVSeriesNoBtn.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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