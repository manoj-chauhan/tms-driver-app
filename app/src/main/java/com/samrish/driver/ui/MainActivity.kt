package com.samrish.driver.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.samrish.driver.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView  = findViewById<BottomNavigationView>(R.id.bottom_tab_view)
        val fragContainerView  = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_assignments -> {
                    var hostController = fragContainerView.findNavController() as NavHostController
                    hostController.navigate(
                        R.id.currentAssignmentsFragment,
                        null,
                        NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(
                            R.id.currentAssignmentsFragment,
                            inclusive = true,
                            saveState = true).build()
                    )
                }
                R.id.tab_history -> {
                    var hostController = fragContainerView.findNavController() as NavHostController
                    hostController.navigate(R.id.example2Fragment)
                }
                else -> {
                    Log.i("Samrish", "Nothing")
                }
            }
            true
        }
    }
}