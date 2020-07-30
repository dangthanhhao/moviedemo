package com.example.moviedemo.screen.main.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.R
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentMovieDetailBinding
import com.example.moviedemo.screen.main.MainActivity
import timber.log.Timber
import javax.inject.Inject

class MovieDetailFragment : BaseFragment() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var binding:FragmentMovieDetailBinding
    lateinit var viewModel: MovieDetailViewModel
    private val args: MovieDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "${args.movieID.toString()}", Toast.LENGTH_SHORT).show()
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_movie_detail,container,false)
        viewModel=ViewModelProviders.of(this,factory).get(MovieDetailViewModel::class.java)
        viewModel.isLoading.value=true

        viewModel.isLoading.observe(this, Observer {
            if(it==true){
                binding.contrainItemLayout.visibility=View.GONE
                binding.progressBar3.visibility=View.VISIBLE
            } else {
                binding.contrainItemLayout.visibility = View.VISIBLE
                binding.progressBar3.visibility = View.GONE
            }
        })
        viewModel.getMovie(args.movieID)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //set up fav icon
        (activity as MainActivity).viewModel.listFavs.observe(binding.lifecycleOwner!!, Observer {
            var isFav = false
            for (item in it) {
                if (args.movieID == item.movieID) {
                    binding.favicon.setImageResource(R.drawable.ic_star_black_24dp)
                    isFav = true
                    break
                }
            }
            if (!isFav) {
                binding.favicon.setImageResource(R.drawable.ic_star_border_black_24dp)
            }
        })

        binding.favicon.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)

            with(builder)
            {
                setTitle("Confirm Favourite")
                setMessage("Are you sure to favour/unfavoured this movie?")
                setPositiveButton("Sure") { dialogInterface, i ->
                    viewModel.setFavouriteMovie(args.movieID, args.title)
//                    (activity as MainActivity).onBackPressed()
                }
                setNegativeButton("Cancel", { dialogInterface, i -> })
                show()
            }
        }


        // Inflate the layout for this fragment
        initRecycleView(binding.listActor)
        return binding.root


    }

    private fun initRecycleView(listActor: RecyclerView) {

        val adapter = ListActorAdapter()
        val fakeData = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
        //(listActor.layoutManager as LinearLayoutManager). orientation= RecyclerView.HORIZONTAL
        listActor.adapter = adapter
        adapter.submitList(fakeData)
        listActor.setHasFixedSize(true)
        Timber.i("List have" + adapter.currentList.size.toString())


    }


}