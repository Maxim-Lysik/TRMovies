package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seealltv

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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesgrab.R
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallovies.SeeAllMoviesAdapter
import com.facebook.shimmer.ShimmerFrameLayout

class SeeAllTVSeriesAdapter (
    val ctx: Context, private val items: ArrayList<ResultSingle>
) : RecyclerView.Adapter<SeeAllTVSeriesAdapter.SeeAllTVSeriesItemsViewHolder>() {

    private var see_all_tvList: ArrayList<ResultSingle> = ArrayList()

    inner class SeeAllTVSeriesItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val see_all_tv_movies_title: TextView = view.findViewById(R.id.see_all_tv_movies_title)
        val see_all_tv_movies_image: ImageView = view.findViewById(R.id.movies_image_see_all_tv)
        val see_all_tv_shimmerframelayout: ShimmerFrameLayout = view.findViewById(R.id.shimmer_view_container_see_all_tv)
    }


    fun bindData(seeAllTVItems: ArrayList<ResultSingle>) {
        see_all_tvList = ArrayList(seeAllTVItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeeAllTVSeriesItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.trending_item_see_all_tv, parent, false)
        return SeeAllTVSeriesItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SeeAllTVSeriesItemsViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (see_all_tvList[position].title == null) {
            holder.see_all_tv_movies_title.text = see_all_tvList[position].name
        } else holder.see_all_tv_movies_title.text = see_all_tvList[position].title




        val drawable = CircularProgressDrawable(ctx)
        drawable.setColorSchemeColors(
            ContextCompat.getColor(
                ctx,
                androidx.appcompat.R.color.background_floating_material_light
            )
        )  // Can change color here
        drawable.strokeWidth = 10f
        drawable.centerRadius = 50f
        drawable.start()




        var image_url = "https://image.tmdb.org/t/p/w500" + see_all_tvList[position].poster_path
        Glide.with(ctx)
            .load(image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.see_all_tv_movies_image);

        if (see_all_tvList.size > 12) {
            holder.see_all_tv_shimmerframelayout.setShimmer(null)
        }

        /*holder.itemView.setOnClickListener {
            Log.d(ContentValues.TAG, "Clicked TV SERIES")
        }*/



        //holder.see_all_tv_movies_release_date.text = see_all_tvList[position].release_date




        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                var bundle = Bundle()
                val our_object = see_all_tvList[position]
                val needed: ResultSingle = see_all_tvList[position]
                bundle.putSerializable("our_obj", needed)
                val activity = ctx as AppCompatActivity

                activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_seeAllTVSeriesFragment_to_webViewFragment, bundle)

                Log.d(ContentValues.TAG, "Clicked inside ASS")
            }
        })




    }

    override fun getItemCount(): Int {
        return see_all_tvList.size
    }


}