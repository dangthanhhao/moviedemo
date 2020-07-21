package com.example.moviedemo.screen.main.fragments.popularmovie

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviedemo.R
import com.example.moviedemo.databinding.FragmentMovieBinding
import com.example.moviedemo.repository.network.PopularMovieListAdapter
import timber.log.Timber


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

        val adapter = PopularMovieListAdapter()
        binding.listPopular.adapter = adapter

        viewModel.moviePagedList.observe(this, Observer {
            adapter.submitList(it)
        }
        )

        viewModel.listFactory.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
            Timber.i("Network state ${it.status}")
        })

        return binding.root
    }


}