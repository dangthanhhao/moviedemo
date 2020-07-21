package com.example.moviedemo.screen.main.fragments.popularmovie

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
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
        binding.listPopular.setHasFixedSize(true)

        viewModel.moviePagedList.observe(this, Observer {
            adapter.submitList(it)
        }
        )
        //fix auto scroll when  first time called
        var callfirstTime = 2
        viewModel.listFactory.networkState.observe(this, Observer {

            val listchanged = adapter.setNetworkState(it)
            Timber.i("List changed: $listchanged")
            if (callfirstTime > 0 && listchanged) {
                binding.listPopular.scrollToPosition(0)
                callfirstTime--
            }

        })

        binding.listPopular.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        return binding.root
    }


}