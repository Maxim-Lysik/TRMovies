package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallovies

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
import com.facebook.shimmer.ShimmerFrameLayout

class SeeAllMoviesAdapter (
    val ctx: Context, private val items: ArrayList<ResultSingle>
) : RecyclerView.Adapter<SeeAllMoviesAdapter.SeeAllItemsViewHolder>() {

    private var see_allList: ArrayList<ResultSingle> = ArrayList()

    inner class SeeAllItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val see_all_movies_title: TextView = view.findViewById(R.id.see_all_movies_title)
        val see_all_movies_image: ImageView = view.findViewById(R.id.movies_image_see_all)
        val see_all_shimmerframelayout: ShimmerFrameLayout = view.findViewById(R.id.shimmer_view_container_see_all)

    }

    fun bindData(seeAllItems: ArrayList<ResultSingle>) {
        see_allList = ArrayList(seeAllItems)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllItemsViewHolder {
        val see_all_itemview = LayoutInflater.from(parent.context)
            .inflate(R.layout.trending_item_see_all_list, parent, false)
        return SeeAllItemsViewHolder(see_all_itemview)
    }


    override fun onBindViewHolder(holder: SeeAllItemsViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (see_allList[position].title == null) {
            holder.see_all_movies_title.text = see_allList[position].name
        } else holder.see_all_movies_title.text = see_allList[position].title


        //holder.see_all_movies_rating.text = see_allList[position].vote_average.toString()


       // holder.movie_rating.text = "0"


      /*  val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.DOWN
        var rating = see_allList[position].vote_average
        val roundoff_rating_movie = df.format(rating)

        holder.see_all_movies_rating.text = roundoff_rating_movie.toString()*/



        //holder.see_all_movies_rating.text = "Released" + see_allList[position].release_date

        //holder.see_all_movies_rating.setText(see_allList[position].release_date)



       // holder.see_all_movies_rating.setTextColor(ContextCompat.getColor(ctx,R.color.yellow_boy))

       // holder.see_all_movies_rating.setBackgroundColor(ContextCompat.getColor(ctx,R.color.yellow_boy))
        // ContextCompat.getColor(ctx,R.color.yellow_boy)


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




        var see_all_image_url = "https://image.tmdb.org/t/p/w500" + see_allList[position].poster_path
        Glide.with(ctx)
            .load(see_all_image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.see_all_movies_image);


        if(see_allList.size>15) {
            holder.see_all_shimmerframelayout.setShimmer(null)
        }

       // holder.see_all_movies_rating.setTextColor(ContextCompat.getColor(ctx,R.color.yellow_boy))




        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                var bundle = Bundle()
                val our_object = see_allList[position]
                val needed: ResultSingle = see_allList[position]
                bundle.putSerializable("our_obj", needed)
                val activity = ctx as AppCompatActivity

                activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_seeAllMoviesfragment_to_webFragmentMovies, bundle)

                Log.d(ContentValues.TAG, "Clicked inside ASS")
            }
        })








    }

    override fun getItemCount(): Int {
        return see_allList.size
    }


}