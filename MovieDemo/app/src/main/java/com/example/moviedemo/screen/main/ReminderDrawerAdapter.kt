package com.example.moviedemo.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.databinding.ListReminderDrawerItemBinding
import com.example.moviedemo.repository.local.ReminderMovieModel
import com.example.moviedemo.screen.main.fragments.reminder.ReminderAdapter
import java.text.SimpleDateFormat


class ReminderDrawerAdapter :
    ListAdapter<ReminderMovieModel, ReminderDrawerAdapter.ReminderDrawerViewHolder>(ReminderAdapter.reminderDiffCallBack) {
    class ReminderDrawerViewHolder(val binding: ListReminderDrawerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReminderMovieModel) {
            val timeRelase = SimpleDateFormat("yyyy-MM-dd HH:mm").format(item.reminderDate)
            val movieName =
                "${item.title} - ${item.releaseDate.substring(0, 4)} - ${item.rating}/10"
            binding.txtItem.text = "$movieName\n$timeRelase"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderDrawerViewHolder {
        return ReminderDrawerViewHolder(
            ListReminderDrawerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReminderDrawerViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}