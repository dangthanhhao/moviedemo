package com.example.moviedemo.screen.main.fragments.popularmovie

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedemo.R
import com.example.moviedemo.databinding.FragmentMovieBinding

const val RECYCLE_VIEW_TYPE = "recycle_type"
const val RECYCLE_LIST_CHANGES = "recycle_changes"

class PopularMovieFragment : Fragment() {

    private var recycleViewType = RecycleViewType.LIST

    //fix auto scroll when  first time called
    private var recycleListChangeCount = 4

    private lateinit var binding: FragmentMovieBinding
    private lateinit var viewModel: PopularMovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(RECYCLE_VIEW_TYPE, recycleViewType)
        outState.putInt(RECYCLE_LIST_CHANGES, recycleListChangeCount)
        super.onSaveInstanceState(outState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (recycleViewType == RecycleViewType.LIST) {
            item.setIcon(R.drawable.ic_view_list_white_24dp)
            recycleViewType = RecycleViewType.GRID
        } else {
            item.setIcon(R.drawable.ic_view_module_white_24dp)
            recycleViewType = RecycleViewType.LIST
        }
        setupRecycleView()

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.filtermenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        savedInstanceState?.getSerializable(RECYCLE_VIEW_TYPE)?.let {
            recycleViewType = it as RecycleViewType
        }

        savedInstanceState?.getInt(RECYCLE_LIST_CHANGES)?.let {
            recycleListChangeCount = it
        }

        val viewModelFactory = PopularMovieViewModelFactory(activity!!.application)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PopularMovieViewModel::class.java)
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setupRecycleView()
        return binding.root
    }

    private fun setupRecycleView() {
        val adapter =
            PopularMovieListAdapter(
                recycleViewType
            )

        val aDevidedLine = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        if (recycleViewType == RecycleViewType.GRID) {
            binding.listPopular.layoutManager = GridLayoutManager(context, 2)
            if (binding.listPopular.itemDecorationCount > 0) {
                binding.listPopular.removeItemDecorationAt(0)
            }

        } else {
            binding.listPopular.addItemDecoration(aDevidedLine)
            binding.listPopular.layoutManager = LinearLayoutManager(context)
        }

        binding.listPopular.adapter = adapter
        binding.listPopular.setHasFixedSize(true)
        viewModel.moviePagedList.observe(this, Observer {
            adapter.submitList(it)
        })


        viewModel.listFactory.networkState.observe(this, Observer {
            val listchanged = adapter.setNetworkState(it)
            if (recycleListChangeCount > 0 && listchanged && recycleViewType == RecycleViewType.LIST) {
                binding.listPopular.scrollToPosition(0)
                recycleListChangeCount--
            }
        })


    }


}