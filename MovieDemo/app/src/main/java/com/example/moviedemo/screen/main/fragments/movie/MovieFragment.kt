package com.example.moviedemo.screen.main.fragments.movie

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.R
import com.example.moviedemo.databinding.FragmentMovieBinding
import com.example.moviedemo.repository.network.Movie
import com.example.moviedemo.util.EndlessRecyclerViewScrollListener
import timber.log.Timber


class BlankFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.filtermenu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModelFactory=MoviePopularViewModelFactory(activity!!.application)
        val viewModel=ViewModelProviders.of(this,viewModelFactory).get(PopularMovieViewModel::class.java)
        val binding=FragmentMovieBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel

        val apdater= ListPopularAdapter()
        binding.listPopular.adapter=apdater


       viewModel.movies.observe(this, Observer {
    Timber.i("ASDASD")
           apdater.submitList(it.toList())

           binding.executePendingBindings()
       })

        val scrollListener = object : EndlessRecyclerViewScrollListener(binding.listPopular.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                viewModel.getMovies(page+1)
                Timber.i("Load more ${page}")
            }
        }

        binding.listPopular.addOnScrollListener(scrollListener)
        // Inflate the layout for this fragment
        return binding.root
    }




}