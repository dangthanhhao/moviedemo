package com.example.moviedemo.screen.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviedemo.R
import com.example.moviedemo.databinding.ActivityMainBinding
import com.example.moviedemo.screen.UserProfileViewModel
import com.example.moviedemo.screen.UserProfileViewModelFactory
import com.example.moviedemo.screen.profile.ProfileActivity
import com.example.moviedemo.util.ReadFilePermisnion


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Main activity","Test")
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
        val viewModelFactory = UserProfileViewModelFactory(application)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel::class.java)
        binding.viewModel = viewModel

        ReadFilePermisnion.verifyStoragePermissions(this)

        test()

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

    @SuppressLint("CheckResult")
    private fun test() {
//        Timber.i("Begib test")
//        var movies=Repository(this).getPopularMovie()
//
//            movies.observeOn(AndroidSchedulers.mainThread()).timeout(3,TimeUnit.SECONDS).onErrorReturn {
//                it.printStackTrace()
//                return@onErrorReturn null
//            }
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//            Timber.i("Got ${it.toString()}")
//        },{
//            it.printStackTrace()
//        },{
//            Timber.i("Complete")
//        })




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