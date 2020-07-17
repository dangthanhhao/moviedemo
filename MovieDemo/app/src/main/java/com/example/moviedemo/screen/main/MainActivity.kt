package com.example.moviedemo.screen.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.moviedemo.screen.profile.ProfileActivity
import com.example.moviedemo.R
import com.example.moviedemo.databinding.ActivityMainBinding
import com.example.moviedemo.repository.Repository
import com.example.moviedemo.repository.local.Database
import com.example.moviedemo.repository.local.getCurrentUser
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        binding.setLifecycleOwner(this)
        //set up Drawer and bottom nav
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        binding.bottomNav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        //set click listener
        binding.buttonEditProfile.setOnClickListener {
            val t=Intent(this, ProfileActivity::class.java)
            startActivity(t)
        }
        // create viewmodel

        val viewModelFactory=MainViewModelFactory(application)
        val viewModel=ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel=viewModel



//        test()

//        var badge = binding.bottomNav.getOrCreateBadge(R.id.favFragment)
//        badge.isVisible = true
//// An icon only badge will be displayed unless a number is set:
//        badge.number = 99

//        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
//            if (nd.id == nc.graph.startDestination) {
//
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//            } else {
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//            }
//        }
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