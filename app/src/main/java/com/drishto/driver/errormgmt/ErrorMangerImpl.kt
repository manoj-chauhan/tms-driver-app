package com.drishto.driver.errormgmt

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.drishto.driver.PhoneNumberActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.ui.viewmodels.errorDescription
import javax.inject.Inject

class ErrorMangerImpl @Inject constructor(
    @ApplicationContext private val context: Context): ErrManager {


    override fun getErrorDescription(context: Context) {
        val intent = Intent(context, PhoneNumberActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun getErrorDescription500(context: Context, error: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Toast.makeText(context, "Something went wrong. Try later", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getErrorDescription401(context: Context, error: String) {
        try {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<errorDescription> = moshi.adapter(errorDescription::class.java)
            val errorResponse = adapter.fromJson(error)

            val handler = Handler(Looper.getMainLooper())

            if (errorResponse != null) {
                handler.post {
                    Toast.makeText(context,  errorResponse.errorDescription, Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, "API Request Failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Error) {
            Toast.makeText(context, "API Request Failed", Toast.LENGTH_SHORT).show()
        }
    }


    override fun handleErrorResponse(context: Context, error: String)
    {
        try {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<errorDescription> = moshi.adapter(errorDescription::class.java)
            val errorResponse = adapter.fromJson(error)

            val handler = Handler(Looper.getMainLooper())

            if (errorResponse != null) {
                handler.post {

                    Toast.makeText(context, errorResponse.errorDescription, Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, "API Request Failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Error) {
            Toast.makeText(context, "API Request Failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getErrorDescription403(context: Context, error: String) {
        try {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<errorDescription> = moshi.adapter(errorDescription::class.java)
            val errorResponse = adapter.fromJson(error)

            val handler = Handler(Looper.getMainLooper())

            if (errorResponse != null) {
                handler.post {

                    Toast.makeText(context, errorResponse.errorDescription, Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, "API Request Failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Error) {
            Toast.makeText(context, "API Request Failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getErrorDescription404(context: Context, error: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Toast.makeText(context, "No url found", Toast.LENGTH_SHORT)
                .show()
        }
    }
}