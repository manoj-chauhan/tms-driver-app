package com.samrish.driver.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.samrish.driver.R

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToCurrentAssignments()
        val bottomNavigationView  = view.findViewById<BottomNavigationView>(R.id.bottom_tab_view)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_assignments -> {
                   goToCurrentAssignments()
                }
                R.id.tab_history -> {
                    goToHistory()
                }
                else -> {
                    Log.i("Samrish", "Nothing")
                }
            }
            true
        }
    }

    private fun goToCurrentAssignments() {
        val ft: FragmentTransaction = (host as AppCompatActivity).supportFragmentManager.beginTransaction()
        ft.replace(R.id.tab_host_fragment, CurrentAssignmentsFragment(), "CurrentAssignmentsFragment")
        ft.commitAllowingStateLoss()
    }

    private fun goToHistory() {
        val ft: FragmentTransaction = (host as AppCompatActivity).supportFragmentManager.beginTransaction()
        ft.replace(R.id.tab_host_fragment, HistoryFragment(), "HistoryFragment")
        ft.commitAllowingStateLoss()
    }

}