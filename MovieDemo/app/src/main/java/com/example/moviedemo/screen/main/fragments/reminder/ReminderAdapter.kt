package com.example.moviedemo.screen.main.fragments.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.moviedemo.databinding.ListReminderItemBinding
import com.example.moviedemo.repository.local.ReminderMovieModel
import com.example.moviedemo.screen.main.fragments.popularmovie.ClickListener
import java.text.SimpleDateFormat

class ReminderAdapter(val navEvent: ClickListener, val deleteEvent:ClickListener) :
    ListAdapter<ReminderMovieModel, ReminderAdapter.ReminderViewHolder>(reminderDiffCallBack) {
    val viewBinderHelper = ViewBinderHelper()

    init {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    class ReminderViewHolder(val binding: ListReminderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            reminder: ReminderMovieModel,
            viewBinderHelper: ViewBinderHelper,
            navEvent: ClickListener,
            deleteEvent: ClickListener
        ) {

            binding.reminder = reminder
            binding.onNavigate=navEvent
            binding.reminderDatetime.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(reminder.reminderDate)
            binding.reminderTitle.text = "${reminder.title} - ${reminder.releaseDate.substring(0,4)} - ${reminder.rating}/10"
            binding.imageShowSwipe.setOnClickListener {
                if (binding.swipeLayout.isClosed) {
                    viewBinderHelper.openLayout(reminder.movieID.toString())
                } else {
                    viewBinderHelper.closeLayout(reminder.movieID.toString())
                }
            }
            binding.onDelete=deleteEvent

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

        holder.bind(getItem(position), viewBinderHelper, navEvent,deleteEvent)
    }

}