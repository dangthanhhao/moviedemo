package com.example.moviedemo.screen.main.fragments.detail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.R
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentMovieDetailBinding
import com.example.moviedemo.screen.main.MainActivity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MovieDetailFragment : BaseFragment() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var binding:FragmentMovieDetailBinding
    lateinit var viewModel: MovieDetailViewModel

    private val args: MovieDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_movie_detail,container,false)
        viewModel=ViewModelProviders.of(this,factory).get(MovieDetailViewModel::class.java)
        viewModel.isLoading.value=true

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.contrainItemLayout.visibility = View.GONE
                binding.progressBar3.visibility = View.VISIBLE
            } else {
                binding.contrainItemLayout.visibility = View.VISIBLE
                binding.progressBar3.visibility = View.GONE
            }
        })
        viewModel.getMovie(args.movieID)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        initFavouriteIcon()
        initRecycleView(binding.listActor)
        viewModel.initReminder(args.movieID)

        binding.buttonSetReminder.setOnClickListener {
            onShowDatePicker(it)
        }

        return binding.root
    }

    private fun initFavouriteIcon() {
        (activity as MainActivity).viewModel.listFavs.observe(binding.lifecycleOwner!!, Observer {
            var isFav = false
            for (item in it) {
                if (args.movieID == item.movieID) {
                    binding.favicon.setImageResource(R.drawable.ic_star_black_24dp)
                    isFav = true
                    break
                }
            }
            if (!isFav) {
                binding.favicon.setImageResource(R.drawable.ic_star_border_black_24dp)
            }
        })

        binding.favicon.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            with(builder)
            {
                setTitle("Confirm Favourite")
                setMessage("Are you sure to favour/unfavoured this movie?")
                setPositiveButton("Sure") { dialogInterface, i ->
                    viewModel.setFavouriteMovie(args.movieID, args.title)
                    Toast.makeText(context, "Favourite setup", Toast.LENGTH_SHORT).show()
                    //                    (activity as MainActivity).onBackPressed()
                }
                setNegativeButton("Cancel", { dialogInterface, i -> })
                show()
            }
        }
    }


    private fun initRecycleView(listActor: RecyclerView) {

        val adapter = ListActorAdapter()
        val fakeData = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
        //(listActor.layoutManager as LinearLayoutManager). orientation= RecyclerView.HORIZONTAL
        listActor.adapter = adapter
        adapter.submitList(fakeData)
        listActor.setHasFixedSize(true)
        Timber.i("List have" + adapter.currentList.size.toString())

    }

    fun onShowDatePicker(view: View) {
//        init current date
        val initDate = viewModel.reminder.value?.reminderDate?:Date()

        val year = initDate.year + 1900
        val month = initDate.month
        val day = initDate.date

        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val datePicked = Date(year - 1900, monthOfYear, dayOfMonth)
                val datePickString = SimpleDateFormat("yyyy-MM-dd").format(datePicked)
                Timber.i("Date picked: $datePickString")
                onShowTimePicker(datePicked)


            },
            year,
            month,
            day
        )

        dpd.show()

    }

    private fun onShowTimePicker(datePicked: Date) {
        val initDate = viewModel.reminder.value?.reminderDate?:Date()
        val hour=initDate.hours
        val minute=initDate.minutes

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { timePicker, hourPicked, minutePicked ->
                Timber.i("TimePicked: $hourPicked , $minutePicked")
                datePicked.hours = hourPicked
                datePicked.minutes = minutePicked
                viewModel.setReminder(datePicked)
                Toast.makeText(context, "Reminder set up!", Toast.LENGTH_SHORT).show()
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()

    }
}