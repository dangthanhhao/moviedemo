package com.example.moviedemo.screen.main.fragments.popularmovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.R
import com.example.moviedemo.databinding.GridPopularMovieItemBinding
import com.example.moviedemo.databinding.ListPopularMovieItemBinding
import com.example.moviedemo.repository.local.FavMovieModel
import com.example.moviedemo.repository.network.Movie
import com.example.moviedemo.repository.network.NetworkState

enum class RecycleViewType {
    LIST,
    GRID
}

class PopularMovieListAdapter(
    val recycleViewType: RecycleViewType = RecycleViewType.LIST,
    val navigateEvent: ClickListener,
    val favEvent: ClickListener,
    val listFav: LiveData<List<FavMovieModel>>
) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(diffCallback) {
    val MOVIE_TYPE = 1
    val NETWORK_TYPE = 2
    private var networkState: NetworkState? = null

    //viewholders
    class PagedPopularMovieViewHolderList(private var binding: ListPopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie?,
            navigateEvent: ClickListener?,
            favEvent: ClickListener?,
            listFav: LiveData<List<FavMovieModel>>
        ) {
            movie?.let {
                binding.movie = movie
                binding.loadingView.visibility = View.GONE
                binding.layoutItem.visibility = View.VISIBLE
                binding.edittextRating.setText(movie.vote_average.toString() + "/10")
                navigateEvent?.let {
                    binding.navigateEvent = navigateEvent
                }
                favEvent?.let {
                    binding.favEvent = favEvent
                }
                binding.lifecycleOwner = this.itemView.context as LifecycleOwner
                listFav.observe(binding.lifecycleOwner!!, Observer {
                    var isFav = false
                    for (item in it) {
                        if (movie.id == item.movieID) {
                            binding.favPopular.setImageResource(R.drawable.ic_star_black_24dp)
                            isFav = true
                            break
                        }
                    }
                    if (!isFav) {
                        binding.favPopular.setImageResource(R.drawable.ic_star_border_black_24dp)
                    }

                })

                binding.executePendingBindings()
            }

        }
    }

    class PagedPopularMovieViewHolderGrid(private var binding: GridPopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?,onClick: ClickListener?) {
            movie?.let {
                binding.movie = movie
                onClick?.let {
                    binding.clickEvent=onClick
                }
                binding.executePendingBindings()
            }

        }
    }

    class NetworkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(networkState: NetworkState?) {
        }
    }

    //diff callback
    object diffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MOVIE_TYPE){
            if (recycleViewType == RecycleViewType.LIST)
                return PagedPopularMovieViewHolderList(
                    ListPopularMovieItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            else return PagedPopularMovieViewHolderGrid(
                GridPopularMovieItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        else return NetworkViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_network_status_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_TYPE) {
            if (recycleViewType == RecycleViewType.LIST)
                (holder as PagedPopularMovieViewHolderList).bind(
                    getItem(position),
                    navigateEvent,
                    favEvent,
                    listFav
                )
            else (holder as PagedPopularMovieViewHolderGrid).bind(getItem(position), navigateEvent)
        } else (holder as NetworkViewHolder).bind(networkState)
    }

    //custom function
    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) NETWORK_TYPE else MOVIE_TYPE
    }

    fun setNetworkState(newNetworkState: NetworkState?): Boolean {
        val preState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        //hadExtraRow is true, has extrarow is false
        //remove progressbar (network viewholder)

        //hadExtraRow is false, has extrarow is true
        //add progressbar

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow)
                notifyItemRemoved(super.getItemCount())
            else
                notifyItemInserted(super.getItemCount())
            return true
        } else if (hasExtraRow && preState != newNetworkState) {
            notifyItemChanged(itemCount - 1)

        }
        return false
    }
}
public class ClickListener(val callback: (movie:Int, name:String)-> Unit){
    fun onCLick(movie: Int, name:String)= callback(movie,name)
}