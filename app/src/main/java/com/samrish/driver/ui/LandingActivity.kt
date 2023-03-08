package com.samrish.driver.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import com.samrish.driver.R

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        val btn  = findViewById<AppCompatButton>(R.id.test_btn)
        val fragContainerView  = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        Log.i("Samrish", "Button $btn")
        btn?.setOnClickListener {
            var hostController = fragContainerView.findNavController() as NavHostController
            Log.i("Samrish", "On Button Clicked $hostController")
            hostController.navigate(R.id.action_exampleFragment_to_example2Fragment)
        };
    }

}