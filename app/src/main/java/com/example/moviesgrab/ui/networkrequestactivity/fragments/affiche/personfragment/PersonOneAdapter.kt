package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.personfragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesgrab.R
import com.example.moviesgrab.networking.person.KnownFor
import com.facebook.shimmer.ShimmerFrameLayout

class PersonOneAdapter (
    val ctx: Context, private val known_for: ArrayList<KnownFor>
) : RecyclerView.Adapter<PersonOneAdapter.OnePersonViewHolder>() {

    private var person_known_for_list: ArrayList<KnownFor> = ArrayList()


    inner class OnePersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val known_for_title: TextView = view.findViewById(R.id.known_for_title)
        val known_for_image: ImageView = view.findViewById(R.id.image_known_for)
        val shimmerknownforframelayout: ShimmerFrameLayout =
            view.findViewById(R.id.shimmer_view_container_known_for)
    }

    fun bindData(known_for_list: ArrayList<KnownFor>) {
        person_known_for_list = ArrayList(known_for_list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonOneAdapter.OnePersonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.known_for_item, parent, false)
        return OnePersonViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: PersonOneAdapter.OnePersonViewHolder, @SuppressLint("RecyclerView") position: Int) {

       // holder.trending_all_person_name.text = trendingAllPersonsList[position].name
        if(person_known_for_list[position].title!= null){
        holder.known_for_title.text = person_known_for_list[position].title
        }
        else if (person_known_for_list[position].name!= null){
            holder.known_for_title.text = person_known_for_list[position].name
        }

       /* var persons_all_image_url = "https://image.tmdb.org/t/p/original" + trendingAllPersonsList[position].profile_path
        Glide.with(ctx)
            .load(persons_all_image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .into(holder.trending_person_image);*/

    //    if (person_known_for_list.size > 0) {
            holder.shimmerknownforframelayout.setShimmer(null)
       // }



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




        var image_url = "https://image.tmdb.org/t/p/w500" + person_known_for_list[position].poster_path
        Glide.with(ctx)
            .load(image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.known_for_image);













        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

               /* var bundle = Bundle()
                val our_object = trendingAllPersonsList[position]
                val needed: SinglePerson = trendingAllPersonsList[position]
                bundle.putSerializable("our_obj", needed)
                val activity = ctx as AppCompatActivity

                //activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(R.id.action_navigation_home_to_webFragmentMovies, bundle)

*/





                Log.d(ContentValues.TAG, "Clicked inside KNOWN FOR")
            }
        })

    }


    override fun getItemCount(): Int {
        return person_known_for_list.size
    }


}