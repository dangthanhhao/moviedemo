package com.example.moviedemo.screen.main.fragments.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentReminderBinding
import com.example.moviedemo.screen.main.fragments.popularmovie.ClickListener
import com.example.moviedemo.util.ViewModelFactory
import javax.inject.Inject


class ReminderFragment : BaseFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var viewmodel: ReminderViewModel
    lateinit var binding: FragmentReminderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewmodel = ViewModelProviders.of(this, factory).get(ReminderViewModel::class.java)

        val adapter = ReminderAdapter(navEvent = ClickListener { movie, name ->
            findNavController().navigate(
                ReminderFragmentDirections.actionReminderFragmentToMovieDetailFragment(
                    movie,
                    name
                )
            )
        }, deleteEvent = ClickListener { movieID, title ->

            val builder = AlertDialog.Builder(context!!)
            with(builder)
            {
                setTitle("Confirm delete")
                setMessage("Are you sure to remove this remind?")
                setPositiveButton("Remove") { dialogInterface, i ->
                    viewmodel.deleteReminder(movieID, title)

                }
                setNegativeButton("Cancel", { dialogInterface, i -> })
                show()
            }


        })
        binding = FragmentReminderBinding.inflate(inflater, container, false)
        binding.listReminder.adapter = adapter
        viewmodel.listReminders.observe(this, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }


}