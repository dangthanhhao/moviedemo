package com.example.moviedemo.screen.main.fragments.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentFavBinding
import com.example.moviedemo.screen.main.fragments.popularmovie.ClickListener
import javax.inject.Inject

class FavFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var viewmodel: FavViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProviders.of(this, factory).get(FavViewModel::class.java)
        val binding = FragmentFavBinding.inflate(inflater, container, false)
        val adapter = FavListAdapter(viewmodel, favEvent = ClickListener { movie, _ ->
            viewmodel.setFavouriteMovie(movie)
        })
        binding.listFav.adapter = adapter


        viewmodel.favIDs.observe(this, Observer {
            adapter.submitList(it)
        })
        //devide line
        binding.listFav.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        return binding.root
    }


}