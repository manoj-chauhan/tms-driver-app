package com.samrish.driver.services

import android.content.Context

class SessionStorage {

    private val authStorage = "AUTHENTICATION"
    private val authTokenKey = "AUTH_TOKEN"
    private val driverIdKey = "DRIVER_ID"

    fun saveAccessToken(context:Context, token:String) {
        val sharedPreference =  context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(authTokenKey, token)
        editor.apply()
    }

    fun saveAccessDriverId(context:Context, driverId: Int) {
        val sharedPreference =  context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt(driverIdKey, driverId)
        editor.apply()
    }

    fun getAccessToken(context:Context): String? {
        return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getString(authTokenKey, null)
    }

    fun getDriverId(context:Context): Int {
        return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getInt(driverIdKey, 0)
    }

    fun clearSession(context: Context) {
        var editor = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}