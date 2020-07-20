package com.example.moviedemo.screen.main.fragments.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.databinding.ListPopularMovieBinding
import com.example.moviedemo.repository.network.Movie

class ListPopularAdapter : ListAdapter<Movie, ListPopularAdapter.ListPopularViewHolder>(DiffCallBack) {

    class ListPopularViewHolder(private var binding: ListPopularMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.edittextRating.setText(movie.vote_average.toString()+"/10")
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPopularViewHolder {
        return ListPopularViewHolder(ListPopularMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ListPopularViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

}