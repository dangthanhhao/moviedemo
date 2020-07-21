package com.example.moviedemo.screen.main.fragments.popularmovie

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviedemo.R
import com.example.moviedemo.databinding.FragmentMovieBinding
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.network.PopularMovieDataSourceFactory
import com.example.moviedemo.repository.network.PopularMovieListAdapter


class PopularMovieFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.filtermenu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModelFactory = PopularMovieViewModelFactory(activity!!.application)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PopularMovieViewModel::class.java)
        val binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

//        val apdater = ListPopularAdapter()
//        binding.listPopular.adapter = apdater
//
//        viewModel.movies.observe(this, Observer {
//            Timber.i("ASDASD")
//            apdater.submitList(it.toList())
//            binding.executePendingBindings()
//        })
        val adapter = PopularMovieListAdapter()
        binding.listPopular.adapter = adapter
        val listFactory = PopularMovieDataSourceFactory(Repository(context!!))
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()
        val moviePagedList = LivePagedListBuilder(listFactory, config).build()

        moviePagedList.observe(this, Observer {
            adapter.submitList(it)
        }
        )

        listFactory.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
//        val scrollListener = object :
//            EndlessRecyclerViewScrollListener(binding.listPopular.layoutManager as LinearLayoutManager) {
//            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//                viewModel.getMovies(page + 1)
//                Timber.i("Load more ${page}")
//            }
//        }
//        binding.listPopular.addOnScrollListener(scrollListener)
//        viewModel.networkState.observe(this, Observer {
//            binding.progressBar.visibility= if( it.status==Status.RUNING) View.VISIBLE else View.GONE
//        })

        // Inflate the layout for this fragment
        return binding.root
    }


}