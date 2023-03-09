package com.samrish.driver.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.samrish.driver.R
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.ui.fragments.LoginFragment
import com.samrish.driver.ui.fragments.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        val token = SessionStorage().getAccessToken(this)
        if(token != null) {
            goToMain()
        }else {
            goToLogin()
        }
        super.onStart()
    }

    private fun goToLogin() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_view, LoginFragment(), "LoginFragment")
        ft.commitAllowingStateLoss()
    }

    private fun goToMain() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_view, MainFragment(), "MainFragment")
        ft.commitAllowingStateLoss()
    }

}