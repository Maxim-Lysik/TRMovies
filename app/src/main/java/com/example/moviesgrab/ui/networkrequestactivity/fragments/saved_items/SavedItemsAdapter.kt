package com.example.moviesgrab.ui.networkrequestactivity.fragments.saved_items

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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesgrab.R
import com.example.moviesgrab.db.SavedItem
import com.facebook.shimmer.ShimmerFrameLayout


class UsersDiffCallBack(
    private val oldList: List<SavedItem>,
    private val newList: List<SavedItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.title_name == newUser.title_name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }

}













class SavedItemsAdapter (
    val ctx: Context, private val items: List<SavedItem>
) : RecyclerView.Adapter<SavedItemsAdapter.SavedItemsAdapterViewHolder>() {

     //var savedItemsList: ArrayList<SavedItem> = ArrayList()
     lateinit var savedItemsList: List<SavedItem>

    inner class SavedItemsAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val saved_item_title: TextView = view.findViewById(R.id.saved_item_text)
        val saved_item_movies_image: ImageView = view.findViewById(R.id.movies_image_saved_item)
        val saved_item_shimmerframelayout: ShimmerFrameLayout = view.findViewById(R.id.shimmer_view_container_saved_item)

    }


    private val differCallback = object : DiffUtil.ItemCallback<SavedItem>() {
        override fun areItemsTheSame(oldItem: SavedItem, newItem: SavedItem): Boolean {
            return oldItem.title_name.toString() == newItem.title_name.toString()
        }

        override fun areContentsTheSame(oldItem: SavedItem, newItem: SavedItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)






    fun bindData(savedItems: List<SavedItem>) {


        savedItemsList = savedItems

        //val list2: List<SavedItem> = listOf(savedItems)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedItemsAdapterViewHolder {
        val saved_itemview = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_item_in_list, parent, false)
        return SavedItemsAdapterViewHolder(saved_itemview)
    }


    override fun onBindViewHolder(holder: SavedItemsAdapterViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (savedItemsList[position].title_name != null) {
            holder.saved_item_title.text = savedItemsList[position].title_name
        }



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



        var see_all_image_url = "https://image.tmdb.org/t/p/w500" + savedItemsList[position].profile_path
        Glide.with(ctx)
            .load(see_all_image_url)
            .placeholder(R.drawable.ic_home_black_24dp)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .placeholder(drawable)
            .into(holder.saved_item_movies_image);


        if(savedItemsList.size>0) {
            holder.saved_item_shimmerframelayout.setShimmer(null)
        }

        /* holder.itemView.setOnClickListener {
             Log.d(ContentValues.TAG, "Clicked inside see_all_item_list")
         }
 */



        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                var bundle = Bundle()
                val our_object = savedItemsList[position]
                val needed: SavedItem = savedItemsList[position]
                bundle.putSerializable("our_obj_saved", needed)
                val activity = ctx as AppCompatActivity

               activity.findNavController(R.id.nav_host_fragment_activity_network_request).navigate(
                    R.id.action_navigation_notifications_to_webFragmentNoBtn, bundle)

                Log.d(ContentValues.TAG, "CLICKED ON SAVED ITEM AIEEE + SITE_ROUTE: ${savedItemsList[position].article_link}")
            }
        })








    }

    override fun getItemCount(): Int {
        return savedItemsList.size
    }


}