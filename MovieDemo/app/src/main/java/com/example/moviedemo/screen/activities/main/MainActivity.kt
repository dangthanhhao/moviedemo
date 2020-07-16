package com.example.moviedemo.screen.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.moviedemo.screen.activities.profile.ProfileActivity
import com.example.moviedemo.R
import com.example.moviedemo.databinding.ActivityMainBinding
import com.example.moviedemo.repository.local.Database
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        binding.bottomNav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)


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

    private fun test() {
        val dao = Database.getInstace(this).UserDao
        //dao.insert(UserModel())
        //Timber.i("Create user")
        val user=dao.get(1L)
//        Timber.i(user.value.toString())
        user.observe(this, Observer {
            it?.let {
                Timber.i(it.toString())
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
//        return  navController.navigateUp();
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val t=Intent(this, ProfileActivity::class.java)
        startActivity(t)
        return super.onOptionsItemSelected(item)
    }
}