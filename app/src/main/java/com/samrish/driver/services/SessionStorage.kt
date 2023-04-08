package com.samrish.driver.services

import android.content.Context
import android.content.SharedPreferences

class SessionStorage {

    private val authStorage = "AUTHENTICATION"
    private val authTokenKey = "AUTH_TOKEN"

    fun saveAccessToken(context:Context, token:String) {
        val sharedPreference =  context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(authTokenKey, token)
        editor.apply()
    }
    fun getAccessToken(context:Context): String? {
        return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getString(authTokenKey, null)
    }

    fun clearSession(context: Context) {
        var editor = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}