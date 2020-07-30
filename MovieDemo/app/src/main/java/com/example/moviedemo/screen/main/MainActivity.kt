package com.example.moviedemo.screen.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviedemo.R
import com.example.moviedemo.base.BaseActivity
import com.example.moviedemo.databinding.ActivityMainBinding
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.screen.UserProfileViewModel
import com.example.moviedemo.screen.profile.ProfileActivity
import com.example.moviedemo.util.ReadFilePermisnion
import timber.log.Timber
import javax.inject.Inject


class MainActivity : BaseActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Main activity", "Test")
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        //set up Drawer and bottom nav
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.myNavHostFragment)
//       appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        appBarConfiguration = AppBarConfiguration(binding.bottomNav.menu, drawerLayout)
        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        //set click listener
        binding.buttonEditProfile.setOnClickListener {
            val t = Intent(this, ProfileActivity::class.java)
            startActivity(t)
        }
        // create viewmodel

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel::class.java)
        binding.viewModel = viewModel

        ReadFilePermisnion.verifyStoragePermissions(this)
test()

    }

    @SuppressLint("CheckResult")
    private fun test() {
//        repository.insertFavMovie(1)
//        repository.insertFavMovie(2)
//        repository.insertFavMovie(3)

//        repository.deleteFavMovie(2)
        val listmovies = repository.getFavMovies()
        listmovies.observe(this, Observer {
            Timber.i("Test: ${it.toString()}")
        })

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
//        return  navController.navigateUp();
        return NavigationUI.navigateUp(navController, drawerLayout)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }
}