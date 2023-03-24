package com.example.moviesgrab.ui.networkrequestactivity.fragments.saved_items

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentNotificationsBinding
import com.example.moviesgrab.db.SavedItem
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.SpacesItemDecoration2
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


//ANDROID ENTRY POINT


@AndroidEntryPoint
class SavedItemsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var savedItemsViewModel: SavedItemsViewModel

    lateinit var savedItemsAdapter: SavedItemsAdapter

    var arraylist_saved_items = arrayListOf<SavedItem>()


   /* override fun onPause() {
        super.onPause()

        binding.noItemsImageview.visibility = View.INVISIBLE
        savedItemsViewModel.getSavedItemsVM().observe(viewLifecycleOwner, Observer { savedItems ->
            //arraylist_saved_items.addAll(savedItems)

            // savedItemsAdapter.differ.submitList(arraylist_saved_items)
            savedItemsAdapter.bindData(savedItems)
            if (savedItems.size > 0) {

                binding.noItemsImageview.visibility = View.INVISIBLE
                //no_items_found.visibility = View.INVISIBLE
                //  ТУТА ПОФИКСИТЬ СИТУЭЙШН С КАРТИНКОЙ
            }})


    }*/



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        savedItemsViewModel =
            ViewModelProvider(this)[SavedItemsViewModel::class.java]


       // var saved_recycler = binding.savedItemsRecycler

       /* val savedItemsViewModel =
            ViewModelProvider(this).get(SavedItemsViewModel::class.java)*/

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkConnectivity()

      /*  val textView: TextView = binding.textNotifications
        savedItemsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        setupSavedItemsRecyclerView(arraylist_saved_items)

        binding.savedItemsRecycler.apply {
            adapter = savedItemsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            this.addItemDecoration(SpacesItemDecoration2(30))
        }


////////////////////////////////////


        savedItemsViewModel.getSavedItemsVM().observe(viewLifecycleOwner, Observer { savedItems ->
            //arraylist_saved_items.addAll(savedItems)

           // savedItemsAdapter.differ.submitList(arraylist_saved_items)

            //var parameterList2 = it.data?.results?.takeWhile { it.backdrop_path != null }
             //   ?.distinctBy { it.title }
           // var parameterList2 = savedItems.distinctBy { it.title_name }

            savedItemsAdapter.bindData(savedItems)
            if (savedItems.size != 0) {

                binding.noItemsImageview.visibility = View.INVISIBLE
                binding.nothingFoundText.visibility = View.INVISIBLE
                //no_items_found.visibility = View.INVISIBLE
                //  ТУТА ПОФИКСИТЬ СИТУЭЙШН С КАРТИНКОЙ
            }




            val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val article = savedItemsAdapter.savedItemsList.get(position)
                    savedItemsViewModel.deleteSavedItemVM(article)
                    Snackbar.make(binding.savedItemsRecycler, "Successfully deleted article", Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo"){
                            if (savedItems.size != 0) {

                                binding.noItemsImageview.visibility = View.INVISIBLE

                            }

                            savedItemsViewModel.saveItem(article)
                        }
                        show()
                    }
                }

            }


            ItemTouchHelper(itemTouchHelperCallback).apply{
                attachToRecyclerView(binding.savedItemsRecycler)
            }














        })






        //////////////////////////






        return root
    }


    private fun setupSavedItemsRecyclerView(savedItemsArrayList: ArrayList<SavedItem>) {
        savedItemsAdapter = SavedItemsAdapter(this.requireActivity(), savedItemsArrayList)
        savedItemsAdapter.bindData(savedItemsArrayList)
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





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}