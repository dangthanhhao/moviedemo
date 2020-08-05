package com.example.moviedemo.screen.main.fragments.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentFavBinding
import com.example.moviedemo.screen.main.fragments.popularmovie.ClickListener
import javax.inject.Inject

class FavFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: FragmentFavBinding
    lateinit var viewmodel: FavViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewmodel = ViewModelProviders.of(this, factory).get(FavViewModel::class.java)
        binding = FragmentFavBinding.inflate(inflater, container, false)

        setupRecycleView()
        setupSearchView()
        setupSwipeToRefresh()
        setHasOptionsMenu(false)
        return binding.root
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshFavourite.apply {
            setOnRefreshListener {
                binding.searchView.setQuery("", true)
                isRefreshing = false
            }
        }
    }

    private fun setupRecycleView() {
        val adapter = FavListAdapter(viewmodel, favEvent = ClickListener { movie, title ->
            val builder = AlertDialog.Builder(requireContext())
            with(builder)
            {
                setTitle("Confirm Favourite")
                setMessage("Are you sure to favour/unfavoured this movie?")
                setPositiveButton("Sure") { dialogInterface, i ->
                    viewmodel.setFavouriteMovie(movie, title)
                }
                setNegativeButton("Cancel", { dialogInterface, i -> })
                show()
            }
        }, navigateEvent = ClickListener { movie, title ->
            findNavController().navigate(
                FavFragmentDirections.actionFavFragmentToMovieDetailFragment(
                    movie,
                    title
                )
            )
        })
        binding.listFav.adapter = adapter

        viewmodel.favIDs.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it)
        })
        //devide line
        binding.listFav.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewmodel.filterKeyword = p0
                viewmodel.filterList()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewmodel.filterKeyword = p0
                viewmodel.filterList()
                return false
            }

        })
    }


}