package com.samrish.driver.errormgmt

import android.content.Context
import android.content.Intent
import android.util.Log
import com.samrish.driver.LoginActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMangerImpl @Inject constructor(
    @ApplicationContext private val context: Context): ErrManager {


    override fun getErrorDescription(context: Context) {
        Log.d("Error", "getErrorDescription: ")
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}