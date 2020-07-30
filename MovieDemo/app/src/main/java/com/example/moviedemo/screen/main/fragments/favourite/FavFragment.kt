package com.example.moviedemo.screen.main.fragments.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentFavBinding
import com.example.moviedemo.screen.main.fragments.popularmovie.ClickListener
import timber.log.Timber
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
        val adapter = FavListAdapter(viewmodel, favEvent = ClickListener { movie, title ->
            viewmodel.setFavouriteMovie(movie, title)
        }, navigateEvent = ClickListener { movie, title ->
            findNavController().navigate(
                FavFragmentDirections.actionFavFragmentToMovieDetailFragment(
                    movie,
                    title
                )
            )
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

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                viewmodel.filterKeyword = p0
                viewmodel.filterList()
                return false
            }

        })
        setHasOptionsMenu(false)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Timber.i("Hit")

        return super.onOptionsItemSelected(item)
    }


}