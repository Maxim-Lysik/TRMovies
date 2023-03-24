package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesgrab.R
import com.example.moviesgrab.networking.ResultSingle
import com.facebook.shimmer.ShimmerFrameLayout
import java.math.RoundingMode
import java.text.DecimalFormat

class TrendingAdapterMovies (
    val ctx: Context, private val days: ArrayList<ResultSingle>
) : RecyclerView.Adapter<TrendingAdapterMovies.TrendingMoviesViewHolder>() {

    // private lateinit var trendinsList: ArrayList<ResultSingle>
    private val limit: Int = 10
    private var trendingMoviesList: ArrayList<ResultSingle> = ArrayList(4)


    inner class TrendingMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trending_title: TextView = view.findViewById(R.id.movies_title)
        val trending_image: ImageView = view.findViewById(R.id.movies_image)
        val shimmerframelayout: ShimmerFrameLayout = view.findViewById(R.id.shimmer_view_container)
        val movie_rating: TextView = view.findViewById(R.id.movie_rating)

    }

    fun bindData(trendingItems: ArrayList<ResultSingle>) {
        trendingMoviesList = ArrayList(trendingItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.treding_item, parent, false)
        return TrendingMoviesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrendingMoviesViewHolder, @SuppressLint("RecyclerView") position: Int) {

        if (trendingMoviesList[position].title == null) {
            holder.trending_title.text = trendingMoviesList[position].name
        } else holder.trending_title.text = trendingMoviesList[position].title

        holder.movie_rating.text = "0"

        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.DOWN
        var rating = trendingMoviesList[position].vote_average
        val roundoff_rating = df.format(rating)

        holder.movie_rating.text = roundoff_rating.toString()


        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))


        val drawable = CircularProgressDrawable(ctx)
        drawable.setColorSchemeColors(
            ContextCompat.getColor(
                ctx,
                androidx.appcompat.R.color.background_floating_material_light
            )
        )  // Can change color here
        drawable.strokeWidth = 15f
        drawable.centerRadius = 70f
        drawable.start()


        var image_url = "https://image.tmdb.org/t/p/w500" + trendingMoviesList[position].poster_path
        Glide.with(ctx)
            .load(image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.trending_image);



        /*

          var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))

        var image_url = "https://image.tmdb.org/t/p/w500" + trendinsList[position].poster_path
        Glide.with(ctx)
            .load(image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .into(holder.trending_image);




        */

        if (trendingMoviesList.size > 5) {
            holder.shimmerframelayout.setShimmer(null)
        }

        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                var bundle = Bundle()
                val our_object = trendingMoviesList[position]
                val needed: ResultSingle = trendingMoviesList[position]
                bundle.putSerializable("our_obj", needed)
                val activity = ctx as AppCompatActivity

                activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_navigation_home_to_webFragmentMovies, bundle)







                Log.d(ContentValues.TAG, "Clicked inside ASS")
            }
        })


        holder.trending_title.setOnClickListener {



        }



    }


    override fun getItemCount(): Int {
        return if (trendingMoviesList.size > limit) {
            limit
        } else {
            trendingMoviesList.size
        }
    }


}
