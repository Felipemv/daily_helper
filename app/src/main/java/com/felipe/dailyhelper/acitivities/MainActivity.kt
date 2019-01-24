package com.felipe.dailyhelper.acitivities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.database.repository.UserRepository
import com.felipe.dailyhelper.util.PreferencesUtil
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private lateinit var navigationController: NavController

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_history -> {
                navigationController.navigate(R.id.historyFragment)
                drawer_layout.closeDrawer(GravityCompat.START)
            }

            R.id.nav_log_work -> {
                navigationController.navigate(R.id.workFragment)
                drawer_layout.closeDrawer(GravityCompat.START)
            }

            R.id.nav_salary -> {
                navigationController.navigate(R.id.salaryFragment)
                drawer_layout.closeDrawer(GravityCompat.START)
            }

            R.id.nav_logout -> {
                PreferencesUtil(this).userId = -1
                PreferencesUtil(this).username = ""
                PreferencesUtil(this).password = ""
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.navigation_header_container -> {
                navigationController.navigate(R.id.profileFragment)
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
    }

    private fun setupNavigation() {
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        navigationController = findNavController(R.id.navigationHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navigationController, drawer_layout)

        val headerView = nav_view.getHeaderView(0)
        headerView.setOnClickListener(this)

        setUser(headerView)
    }

    private fun setUser(headerView: View) {
        userId = PreferencesUtil(this).userId
        val user = UserRepository.getInstance(this).find(userId)

        if (user != null) {
            headerView.findViewById<TextView>(R.id.nav_header_tv_name).text = user.name
            headerView.findViewById<TextView>(R.id.nav_header_tv_username).text = user.username
        } else {
            Toast.makeText(this, "Could not load user", Toast.LENGTH_SHORT).show()
        }
    }

}
