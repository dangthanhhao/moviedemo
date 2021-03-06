package com.example.moviedemo.screen.main

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
import com.example.moviedemo.screen.main.fragments.popularmovie.PopularMovieFragmentDirections
import com.example.moviedemo.screen.main.fragments.setting.SettingFragmentDirections
import com.example.moviedemo.screen.profile.ProfileActivity
import com.example.moviedemo.util.ReadFilePermisnion
import javax.inject.Inject


class MainActivity : BaseActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: UserProfileViewModel

    @Inject
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        //init viewmodel, binding
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel::class.java)
        binding.viewModel = viewModel

        setUpDrawerAndBottomNav()
        // Check and request permision for read write internal files
        ReadFilePermisnion.verifyStoragePermissions(this)

        //get the 2 reminders for drawer
        setupRemindersDrawer()
        checkStartFromNotification()

    }

    private fun setUpDrawerAndBottomNav() {
        //set up Drawer and bottom nav
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(binding.bottomNav.menu, drawerLayout)

        binding.bottomNav.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //set click listener on Edit profile button drawer
        binding.buttonEditProfile.setOnClickListener {
            val t = Intent(this, ProfileActivity::class.java)
            startActivity(t)
        }
        binding.buttonShowReminders.setOnClickListener {
            navController.navigate(R.id.settingFragment)
            navController.navigate(SettingFragmentDirections.actionSettingFragmentToReminderFragment())
            binding.drawerLayout.closeDrawers()
        }
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
    }

    private fun setupRemindersDrawer() {
        val adapter = ReminderDrawerAdapter()
        binding.listReminderDrawer.adapter = adapter
        viewModel.list2RecentReminders.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun checkStartFromNotification() {
        intent.extras.let {
            if (it != null && it.getBoolean("startFromNotification", false)) {
                val movieid = it.getInt("movieid")
                val title = it.getString("title")
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(
                    PopularMovieFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                        movieid,
                        title!!
                    )
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        checkStartFromNotification()
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return navController.navigateUp(appBarConfiguration)
    }

    //bottom nav
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }
}