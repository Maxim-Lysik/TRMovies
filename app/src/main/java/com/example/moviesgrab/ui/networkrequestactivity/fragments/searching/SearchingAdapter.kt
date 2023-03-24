package com.example.moviesgrab.ui.networkrequestactivity.fragments.searching

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesgrab.R
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.networking.person.SinglePerson
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.TrendingPersonsAdapter
import com.example.moviesgrab.ui.networkrequestactivity.fragments.searching.dataMovies.SearchedResultMovie
import com.facebook.shimmer.ShimmerFrameLayout


class SearchingAdapter (
    val ctx: Context
) : RecyclerView.Adapter<SearchingAdapter.SearchingViewHolder>() {




    inner class SearchingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val found_item_name: TextView = view.findViewById(R.id.title_found_item)
        val found_item_image: ImageView = view.findViewById(R.id.image_found_item)
        val shimmer_found_item_framelayout: ShimmerFrameLayout =
            view.findViewById(R.id.shimmer_view_container_found_item)
    }



    private val differCallback = object : DiffUtil.ItemCallback<ResultSingle>() {
        override fun areItemsTheSame(oldItem: ResultSingle, newItem: ResultSingle): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ResultSingle, newItem: ResultSingle): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchingViewHolder {
        return SearchingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.found_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((ResultSingle) -> Unit)? = null


    override fun onBindViewHolder(holder: SearchingAdapter.SearchingViewHolder, @SuppressLint("RecyclerView") position: Int) {

        var pict_link: String


        val foundItem = differ.currentList[position]

        if(foundItem.backdrop_path != null){
            pict_link = foundItem.poster_path.toString()
        } else {pict_link = foundItem.poster_path.toString()
        }

        holder.found_item_name.text = differ.currentList[position].title


       // var person_image_url = "https://image.tmdb.org/t/p/original" + foundItem.backdrop_path



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





        var person_image_url = "https://image.tmdb.org/t/p/original" + pict_link
        Glide.with(ctx)
            .load(person_image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.found_item_image);

        if (differ.currentList.size > 0) {
            holder.shimmer_found_item_framelayout.setShimmer(null)
        }

        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {


                var bundle = Bundle()

                val our_object  = differ.currentList[position]
                val needed: ResultSingle = differ.currentList[position]

                //val our_object = see_allList[position]
                //val needed: ResultSingle = see_allList[position]
                bundle.putSerializable("our_obj2", needed)
                val activity = ctx as AppCompatActivity

                activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_navigation_dashboard_to_foundFragment, bundle)

                Log.d(ContentValues.TAG, "Clicked inside ASS")


                Log.d(ContentValues.TAG, "CHECK DIS ONE &${foundItem.title}  and ID IS ${foundItem.id}")
            }
        })



    }




    fun setOnItemClickListener(listener: (ResultSingle) -> Unit) {
        onItemClickListener = listener
    }
}
