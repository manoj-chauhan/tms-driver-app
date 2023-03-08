package com.samrish.driver.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.samrish.driver.R

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        val bottomNavigationView  = findViewById<BottomNavigationView>(R.id.bottom_tab_view)
        val fragContainerView  = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_assignments -> {
                    var hostController = fragContainerView.findNavController() as NavHostController
                    hostController.navigate(R.id.exampleFragment)
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