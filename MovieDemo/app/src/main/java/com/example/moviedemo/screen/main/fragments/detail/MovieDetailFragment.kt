package com.example.moviedemo.screen.main.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedemo.R
import com.example.moviedemo.base.BaseFragment
import com.example.moviedemo.databinding.FragmentMovieDetailBinding
import com.example.moviedemo.repository.network.Movie
import timber.log.Timber
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
            }
            else{
                binding.contrainItemLayout.visibility=View.VISIBLE
                binding.progressBar3.visibility=View.GONE
            }
        })
        viewModel.getMovie(args.movieID)
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this

        // Inflate the layout for this fragment
        initRecycleView(binding.listActor)
        return binding.root


    }

    private fun initRecycleView(listActor: RecyclerView) {
        val adapter= ListActorAdapter()
        val fakeData= listOf<Int>(1,2,3,4,5,6,7)
        binding.listActor.adapter=adapter
        adapter.submitList(fakeData)
        Timber.i("List have"+adapter.currentList.size.toString())

    }


}