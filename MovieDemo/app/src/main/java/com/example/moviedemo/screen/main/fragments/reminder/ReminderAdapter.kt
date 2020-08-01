package com.example.moviedemo.screen.main.fragments.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.moviedemo.databinding.ListReminderItemBinding
import com.example.moviedemo.repository.local.ReminderMovieModel
import java.text.SimpleDateFormat

class ReminderAdapter :
    ListAdapter<ReminderMovieModel, ReminderAdapter.ReminderViewHolder>(reminderDiffCallBack) {
    val viewBinderHelper = ViewBinderHelper()

    init {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    class ReminderViewHolder(val binding: ListReminderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            reminder: ReminderMovieModel,
            viewBinderHelper: ViewBinderHelper
        ) {

            binding.reminder = reminder
            binding.reminderDatetime.text =
                SimpleDateFormat("yyyy-MM-dd hh:mm").format(reminder.reminderDate)
            binding.reminderTitle.text =
                "${reminder.title} - ${reminder.releaseDate.substring(reminder.releaseDate.length - 4)} - ${reminder.rating}/10"
            binding.imageShowSwipe.setOnClickListener {
                if (binding.swipeLayout.isClosed) {
                    viewBinderHelper.openLayout(reminder.movieID.toString())
                } else {
                    viewBinderHelper.closeLayout(reminder.movieID.toString())
                }
            }
            binding.deleteReminder.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "Delete ${reminder.movieID}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object reminderDiffCallBack : DiffUtil.ItemCallback<ReminderMovieModel>() {
        override fun areItemsTheSame(
            oldItem: ReminderMovieModel,
            newItem: ReminderMovieModel
        ): Boolean {
            return oldItem.movieID == newItem.movieID
        }

        override fun areContentsTheSame(
            oldItem: ReminderMovieModel,
            newItem: ReminderMovieModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val binding =
            ListReminderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        viewBinderHelper.bind(holder.binding.swipeLayout, getItem(position).movieID.toString())

        holder.bind(getItem(position), viewBinderHelper)
    }

}