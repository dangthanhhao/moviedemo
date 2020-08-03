package com.example.moviedemo.screen.main.fragments.detail

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat

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
import com.example.moviedemo.util.NotificationSetter
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "${args.movieID.toString()}", Toast.LENGTH_SHORT).show()
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_movie_detail,container,false)
        viewModel=ViewModelProviders.of(this,factory).get(MovieDetailViewModel::class.java)
        viewModel.isLoading.value=true

        viewModel.isLoading.observe(this, Observer {
            if(it==true){
                binding.contrainItemLayout.visibility=View.GONE
                binding.progressBar3.visibility=View.VISIBLE
            } else {
                binding.contrainItemLayout.visibility = View.VISIBLE
                binding.progressBar3.visibility = View.GONE
            }
        })
        viewModel.getMovie(args.movieID)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //set up fav icon
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
            val builder = AlertDialog.Builder(context!!)

            with(builder)
            {
                setTitle("Confirm Favourite")
                setMessage("Are you sure to favour/unfavoured this movie?")
                setPositiveButton("Sure") { dialogInterface, i ->
                    viewModel.setFavouriteMovie(args.movieID, args.title)
//                    (activity as MainActivity).onBackPressed()
                }
                setNegativeButton("Cancel", { dialogInterface, i -> })
                show()
            }
        }


        // Inflate the layout for this fragment
        initRecycleView(binding.listActor)
        viewModel.initReminder(args.movieID)

        binding.buttonSetReminder.setOnClickListener {
            onShowDatePicker(it)

        }


        viewModel.reminder.observe(this, Observer {
            Timber.i("Current remider $it")
        })
        return binding.root


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
            context!!,
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

        val timePickerDialog=TimePickerDialog(context!!,TimePickerDialog.OnTimeSetListener { timePicker, hourPicked, minutePicked ->
            Timber.i("TimePicked: $hourPicked , $minutePicked" )
            datePicked.hours=hourPicked
            datePicked.minutes=minutePicked

            viewModel.setReminder(datePicked)

            NotificationSetter.setNotication(args.movieID,args.title,context!!,datePicked)
        },hour,minute,true)
        timePickerDialog.show()

    }
}