package com.example.moviedemo.repository.network

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.R

import com.example.moviedemo.databinding.ListPopularMovieItemBinding

class PopularMovieListAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(diffCallback) {
    val MOVIE_TYPE = 1
    val NETWORK_TYPE = 2
    private var networkState: NetworkState? = null

    //viewholders
    class PagedPopularMovieViewHolder(private var binding: ListPopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) {
            movie?.let {
                binding.movie = movie
                binding.edittextRating.setText(movie.vote_average.toString() + "/10")
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
        if (viewType == MOVIE_TYPE)
            return PagedPopularMovieViewHolder(
                ListPopularMovieItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        else return NetworkViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_network_status_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_TYPE) {
            (holder as PagedPopularMovieViewHolder).bind(getItem(position))
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

    fun setNetworkState(newNetworkState: NetworkState?) {
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
        } else if (hasExtraRow && preState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}