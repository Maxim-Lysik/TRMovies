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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesgrab.R
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.networking.person.SinglePerson
import com.facebook.shimmer.ShimmerFrameLayout

class TrendingPersonsAdapter(
    val ctx: Context, private val days: ArrayList<SinglePerson>
) : RecyclerView.Adapter<TrendingPersonsAdapter.TrendingPersonsViewHolder>() {

    private val limit: Int = 6
    private var trendingPersonsList: ArrayList<SinglePerson> = ArrayList()


    inner class TrendingPersonsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trending_person_name: TextView = view.findViewById(R.id.person_name)
        val trending_person_image: ImageView = view.findViewById(R.id.person_image)
        val shimmerframelayout: ShimmerFrameLayout =
            view.findViewById(R.id.shimmer_view_persons_container)
    }

    fun bindData(trendingpersons: ArrayList<SinglePerson>) {
        trendingPersonsList = ArrayList(trendingpersons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingPersonsAdapter.TrendingPersonsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_id, parent, false)
        return TrendingPersonsViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: TrendingPersonsAdapter.TrendingPersonsViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.trending_person_name.text = trendingPersonsList[position].name




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



        var person_image_url = "https://image.tmdb.org/t/p/original" + trendingPersonsList[position].profile_path
        Glide.with(ctx)
            .load(person_image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.trending_person_image);

        if (trendingPersonsList.size > 5) {
            holder.shimmerframelayout.setShimmer(null)
        }








        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                var bundle = Bundle()
                val our_object = trendingPersonsList[position]
                val needed: SinglePerson = trendingPersonsList[position]
                bundle.putSerializable("our_person", our_object)
                val activity = ctx as AppCompatActivity

               // activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_navigation_home_to_personFragment, bundle)

             //   activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_navigation_home_to_webViewFragment, bundle)

                activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_navigation_home_to_personFragment, bundle)



                Log.d(ContentValues.TAG, "Clicked inside ASSEE")
            }
        })


      /*  holder.trending_title.setOnClickListener {

        }*/



    }


    override fun getItemCount(): Int {
        return if (trendingPersonsList.size > limit) {
            limit
        } else {
            trendingPersonsList.size
        }
    }






}