package com.example.moviedemo.screen.main.fragments.popularmovie

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedemo.R
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentMovieBinding
import javax.inject.Inject

const val RECYCLE_VIEW_TYPE = "recycle_type"
const val RECYCLE_LIST_CHANGES = "recycle_changes"
const val GRID_COLUMNS = 2
class PopularMovieFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMovieBinding
    private lateinit var viewModel: PopularMovieViewModel

    private var recycleViewType = RecycleViewType.LIST

    //fix auto scroll when  first time called
    private var recycleListChangeCount = 4

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(RECYCLE_VIEW_TYPE, recycleViewType)
        outState.putInt(RECYCLE_LIST_CHANGES, recycleListChangeCount)
        super.onSaveInstanceState(outState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != R.id.filter_menu) return super.onOptionsItemSelected(item)
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
        inflater.inflate(R.menu.filtermenu, menu)
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


        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PopularMovieViewModel::class.java)
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setupRecycleView()
        return binding.root
    }

    private fun setupRecycleView() {

        val adapter =
            PopularMovieListAdapter(recycleViewType, navigateEvent = ClickListener { movie, name ->
                findNavController().navigate(
                    PopularMovieFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                        movie,
                        name
                    )
                )
            }, favEvent = ClickListener { movie, name ->
                val builder = AlertDialog.Builder(context!!)

                with(builder)
                {
                    setTitle("Confirm Favourite")
                    setMessage("Are you sure to favour/unfavoured this movie?")
                    setPositiveButton("Sure") { dialogInterface, i ->
                        viewModel.setFavouriteMovie(movie, name)
                        Toast.makeText(context, "Marked as favourite", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Cancel", { dialogInterface, i -> })
                    show()
                }


            }, listFav = viewModel.listFav)

        val aDevidedLine = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        if (recycleViewType == RecycleViewType.GRID) {


            val gridLayoutManager = GridLayoutManager(context, GRID_COLUMNS)
            binding.listPopular.layoutManager = gridLayoutManager
            //remove devide line if has
            if (binding.listPopular.itemDecorationCount > 0) {
                binding.listPopular.removeItemDecorationAt(0)
            }
            //span loading-item to 2 columns
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = adapter.getItemViewType(position)
                    if (viewType == adapter.NETWORK_TYPE) {
                        return GRID_COLUMNS
                    } else return 1
                }

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