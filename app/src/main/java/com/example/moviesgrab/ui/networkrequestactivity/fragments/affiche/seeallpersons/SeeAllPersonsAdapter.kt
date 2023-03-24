package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallpersons

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
import com.example.moviesgrab.networking.person.SinglePerson
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.TrendingPersonsAdapter
import com.facebook.shimmer.ShimmerFrameLayout

class SeeAllPersonsAdapter (
    val ctx: Context, private val days: ArrayList<SinglePerson>
) : RecyclerView.Adapter<SeeAllPersonsAdapter.SeeAllTrendingPersonsViewHolder>() {

   // private val limit: Int = 10
    private var trendingAllPersonsList: ArrayList<SinglePerson> = ArrayList()


    inner class SeeAllTrendingPersonsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trending_all_person_name: TextView = view.findViewById(R.id.see_all_persons_name)
        val trending_person_image: ImageView = view.findViewById(R.id.persons_image_see_all)
        val shimmerAllframelayout: ShimmerFrameLayout =
            view.findViewById(R.id.shimmer_view_container_see_all_persons)
    }

    fun bindData(trendingpersons: ArrayList<SinglePerson>) {
        trendingAllPersonsList = ArrayList(trendingpersons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllPersonsAdapter.SeeAllTrendingPersonsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.trending_item_see_all_persons, parent, false)
        return SeeAllTrendingPersonsViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: SeeAllPersonsAdapter.SeeAllTrendingPersonsViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.trending_all_person_name.text = trendingAllPersonsList[position].name






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

        /*
        drawable.strokeWidth = 10f
        drawable.centerRadius = 60f
        * */





        var persons_all_image_url = "https://image.tmdb.org/t/p/original" + trendingAllPersonsList[position].profile_path
        Glide.with(ctx)
            .load(persons_all_image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.trending_person_image);

        if (trendingAllPersonsList.size > 15) {
            holder.shimmerAllframelayout.setShimmer(null)
        }

        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                var bundle = Bundle()
                val our_object = trendingAllPersonsList[position]
                val needed: SinglePerson = trendingAllPersonsList[position]
                bundle.putSerializable("our_person", our_object)
                val activity = ctx as AppCompatActivity

                activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_seeAllPersonsFragment_to_personFragment, bundle)







                Log.d(ContentValues.TAG, "Clicked inside ASS")
            }
        })


        /*  holder.trending_title.setOnClickListener {

          }*/



    }


    override fun getItemCount(): Int {
        return trendingAllPersonsList.size
    }






}
