package com.example.moviedemo.screen.main.fragments.favourite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.R
import com.example.moviedemo.databinding.ListPopularMovieItemBinding
import com.example.moviedemo.repository.local.FavMovieModel
import com.example.moviedemo.screen.main.fragments.popularmovie.ClickListener
import org.jetbrains.annotations.NotNull

class FavListAdapter(
    val viewmodel: FavViewModel,
    val favEvent: ClickListener?,
    val navigateEvent: ClickListener
) :
    ListAdapter<FavMovieModel, FavListAdapter.FavViewHolder>(favDiffCallback) {

    class FavViewHolder(val binding: @NotNull ListPopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(
            item: FavMovieModel,
            viewmodel: FavViewModel,
            favEvent: ClickListener?,
            navigateEvent: ClickListener
        ) {
            binding.loadingView.visibility = View.VISIBLE
            binding.layoutItem.visibility = View.INVISIBLE
            viewmodel.getMovieDetail(item.movieID).subscribe({
                binding.movie = it

                binding.edittextRating.text = it.vote_average.toString() + "/10"
                binding.favPopular.setImageResource(R.drawable.ic_star_black_24dp)

                favEvent?.let {
                    binding.favEvent = favEvent
                }
                binding.navigateEvent = navigateEvent
                binding.loadingView.visibility = View.GONE
                binding.layoutItem.visibility = View.VISIBLE
            }, { it.printStackTrace() })

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(
            ListPopularMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(getItem(position), viewmodel, favEvent, navigateEvent)
    }

    object favDiffCallback : DiffUtil.ItemCallback<FavMovieModel>() {
        override fun areItemsTheSame(oldItem: FavMovieModel, newItem: FavMovieModel): Boolean {
            return oldItem.movieID == newItem.movieID
        }

        override fun areContentsTheSame(oldItem: FavMovieModel, newItem: FavMovieModel): Boolean {
            return oldItem == newItem
        }
    }


}