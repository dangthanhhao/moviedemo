package com.example.moviedemo.screen.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
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
    lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        //init viewmodel, binding
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel::class.java)
        binding.viewModel = viewModel

        //set up Drawer and bottom nav
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.myNavHostFragment)
//       appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        appBarConfiguration = AppBarConfiguration(binding.bottomNav.menu, drawerLayout)
//        appBarConfiguration= AppBarConfiguration(setOf(R.id.homeFragment,R.id.favFragment),drawerLayout)
        binding.bottomNav.setupWithNavController(navController)

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //set click listener on Edit profile button drawer
        binding.buttonEditProfile.setOnClickListener {
            val t = Intent(this, ProfileActivity::class.java)
            startActivity(t)
        }

        // Check and request permision for read write internal files
        ReadFilePermisnion.verifyStoragePermissions(this)
        //setup badge on bottom nav
        viewModel.numFavs.observe(this, Observer {

            val badge = binding.bottomNav.getOrCreateBadge(R.id.favFragment)
            if (it == 0)
                badge.isVisible = false
            else {
                badge.isVisible = true
                badge.number = it
            }

        })



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
        return navController.navigateUp(appBarConfiguration);
    }

    //bottom nav
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }
}