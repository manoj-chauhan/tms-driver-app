package com.samrish.driver.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.samrish.driver.R

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView  = view.findViewById<BottomNavigationView>(R.id.bottom_tab_view)
        val fragContainerView  = view.findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_assignments -> {
                    val hostController = fragContainerView.findNavController() as NavHostController
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
                    val hostController = fragContainerView.findNavController() as NavHostController
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