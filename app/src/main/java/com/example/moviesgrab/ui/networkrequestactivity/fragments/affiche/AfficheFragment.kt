package com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche



import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.moviesgrab.Constants.item1_shm
import com.example.moviesgrab.Constants.person1
import com.example.moviesgrab.R
import com.example.moviesgrab.databinding.FragmentAfficheBinding
import com.example.moviesgrab.networking.FetchTrendingUseCase
import com.example.moviesgrab.networking.ResultSingle
import com.example.moviesgrab.networking.person.SinglePerson
import com.example.moviesgrab.ui.networkrequestactivity.fragments.affiche.seeallovies.SeeAllMoviesfragment
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class AfficheFragment : Fragment(), TrendingListViewMvc.Listener {

    private var _binding: FragmentAfficheBinding? = null

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject
    lateinit var fetchTrendingUseCase: FetchTrendingUseCase
    // @Inject lateinit var viewMvcFactory: ViewMvcFactory

    private lateinit var viewMvc: TrendingListViewMvc
    private var isDataLoaded = false
    lateinit var trendingMovieAdapter: TrendingAdapterMovies
    lateinit var trendingTvAdapter: TrendingAdapter
    lateinit var trendingPersonsAdapter: TrendingPersonsAdapter

    var images_url_array: ArrayList<String> = ArrayList()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val afficheViewModel =
            ViewModelProvider(this).get(AfficheViewModel::class.java)


        _binding = FragmentAfficheBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val seeAllMoviesfragment = SeeAllMoviesfragment()

        val textSee: TextView = binding.seeallmovies
        textSee.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllMoviesfragment)
        }

        val image_see_all_movies = binding.imageAllMovies

        image_see_all_movies.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllMoviesfragment)
        }



        val textSeeAllTV: TextView = binding.seealltvseries
        textSeeAllTV.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllTVSeriesFragment)
        }

        val image_see_all_series = binding.imageAllSeries

        image_see_all_series.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllTVSeriesFragment)
        }




        val image_see_all_persons = binding.imageAllPersons

        image_see_all_persons.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllPersonsFragment)
        }

        val textSeeAllPersonsText: TextView = binding.popularActors
        textSeeAllPersonsText.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllPersonsFragment)
        }

        //val trending_itemview:View = binding.trendingMoviesRecycler.findViewById(R.layout.treding_item)





        val array_movies = arrayListOf<ResultSingle>()
        val array_tv = arrayListOf<ResultSingle>()
        val array_persons = arrayListOf<SinglePerson>()

        array_movies.add(item1_shm)
        array_movies.add(item1_shm)
        array_movies.add(item1_shm)
        array_movies.add(item1_shm)
        array_tv.add(item1_shm)
        array_tv.add(item1_shm)
        array_tv.add(item1_shm)
        array_tv.add(item1_shm)
        array_persons.add(person1)
        array_persons.add(person1)
        array_persons.add(person1)
        array_persons.add(person1)
        setupTrendingMoviesRecyclerView(array_movies)
        setupTrendingTVRecyclerView(array_tv)
        setupTrendingPersonsRecyclerView(array_persons)



        binding.trendingMoviesRecycler.apply {
            adapter = trendingMovieAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

this.addItemDecoration(SpacesItemDecoration(50))

/*

mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));




 */


        }




        binding.trendingTvRecycler.apply {
            adapter = trendingTvAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            this.addItemDecoration(SpacesItemDecoration(50))

        }

        binding.trendingActors.apply {
            adapter = trendingPersonsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

             this.addItemDecoration(SpacesItemDecoration(50))

        }


        afficheViewModel.result_items_movie.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                array_movies.removeAll(array_movies)
                array_movies.addAll(it)

                val sub_array_for_images = it.subList(15, 20)
                // images_url_array.add()

                for (item in sub_array_for_images) {
                    println("CHECKING DA SHEIT + ${item.poster_path}")
                    images_url_array.add(item.poster_path.toString())
                }

                lifecycleScope.launch {
                    binding.shimmerViewContainerImage.setShimmer(null)
                    loadingImagesWithDelay(images_url_array)
                }


                //val image_url = it[2].poster_path
                trendingMovieAdapter.bindData(array_movies)
            })

        afficheViewModel.result_items_tv.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            array_tv.removeAll(array_tv)
            array_tv.addAll(it)
            trendingTvAdapter.bindData(array_tv)
        })

        afficheViewModel.result_popular_persons.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                array_persons.removeAll(array_persons)
                array_persons.addAll(it)
                trendingPersonsAdapter.bindData(array_persons)
            })




        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(clickedItem: ResultSingle) {
        Log.d(ContentValues.TAG, "Clicked SMTH")
    }


    private fun setupTrendingMoviesRecyclerView(newList: ArrayList<ResultSingle>) {
        trendingMovieAdapter = TrendingAdapterMovies(this.requireActivity(), newList)
        trendingMovieAdapter.bindData(newList)
    }

    private fun setupTrendingTVRecyclerView(newList: ArrayList<ResultSingle>) {
        trendingTvAdapter = TrendingAdapter(this.requireActivity(), newList)
        trendingTvAdapter.bindData(newList)
    }

    private fun setupTrendingPersonsRecyclerView(newList: ArrayList<SinglePerson>) {
        trendingPersonsAdapter = TrendingPersonsAdapter(this.requireActivity(), newList)
        trendingPersonsAdapter.bindData(newList)
    }




    suspend fun loadingImagesWithDelay(urls: ArrayList<String>) = coroutineScope {
        println("AYEYEYEYET DA 22 ")


        val drawable = CircularProgressDrawable(requireActivity())
        drawable.setColorSchemeColors(
            ContextCompat.getColor(
                requireActivity(),
                androidx.appcompat.R.color.background_floating_material_light
            )
        )  // Can change color here
        drawable.strokeWidth = 15f
        drawable.centerRadius = 70f
        drawable.start()


// drawable.strokeWidth = 10f
//        drawable.centerRadius = 60f
        // .background_floating_material_light  material_blue_grey_800


        var clider1: ImageView = requireView().findViewById(R.id.image_slider)
        var clider2: ImageView = requireView().findViewById(R.id.image_slider2)
        var shimmer_image: ShimmerFrameLayout = requireView().findViewById(R.id.shimmer_view_container_image)
        try{

        launch() {
            var i = 0
            while (true) {
                println("AYEYEYEYET DA 223 + ${urls[i]}")

                withContext(Dispatchers.Main){

                    println("INSIDE MAIN TRADU")
                    var image_url = "https://image.tmdb.org/t/p/w500" + urls[i]
                    Glide.with(this@AfficheFragment)
                        .load(image_url)
                       // .override(1000,500)
                       // .placeholder(R.drawable.ic_home_black_24dp)
                        .fitCenter()
                        .centerCrop()
                        .placeholder(drawable)
                        .into(clider1)

                    shimmer_image.setShimmer(null)
                    i++
                    delay(3000)

                    Glide.with(this@AfficheFragment)
                        .load(image_url)
                     //   .override(1000,500)
                   //      .placeholder(R.drawable.ic_home_black_24dp)

                        .fitCenter()
                        .centerCrop()
                        .placeholder(drawable)
                        .into(clider2)
                    delay(3000)
                }

               // i++
                if(i>urls.size-1)
                {
                    i=0
                }
               // delay(5000)
            }
        }



    } catch(e: NullPointerException) {}



    }

}

