package com.samrish.driver

import android.content.Context

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
        return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getString(authTokenKey, "")
    }
}