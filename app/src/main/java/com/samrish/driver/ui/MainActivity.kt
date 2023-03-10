package com.samrish.driver.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import com.samrish.driver.R
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.ui.fragments.LoginFragment
import com.samrish.driver.ui.fragments.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        val token = SessionStorage().getAccessToken(this)
        if(token != null) {
            goToMain()
        }else {
            goToLogin()
        }
        super.onResume()
    }

    private fun goToLogin() {
        val fragContainerView  = findViewById<FragmentContainerView>(R.id.nav_host_frag)
        var hostController = fragContainerView.findNavController() as NavHostController
        hostController.navigate(R.id.action_mainFragment2_to_loginFragment2)


//        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
//        ft.replace(R.id.main_view, LoginFragment(), "LoginFragment")
//        ft.commitAllowingStateLoss()
    }

    private fun goToMain() {
//        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
//        ft.replace(R.id.main_view, MainFragment(), "MainFragment")
//        ft.commitAllowingStateLoss()
    }

}