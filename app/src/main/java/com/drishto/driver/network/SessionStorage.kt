package com.drishto.driver.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


private val authStorage = "AUTHENTICATION"
private val authTokenKey = "AUTH_TOKEN"
private val driverIdKey = "DRIVER_ID"
private val profileId = "PROFILE_ID"
private val companyCodeKey = "COMPANY_CODE"
private val companyIdKey = "COMPANY_ID"
private val userId = "USER_ID"


fun saveAccessToken(context: Context, token: String) {
    val sharedPreference = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString(authTokenKey, token)
    editor.apply()
}

fun saveProfileId(context: Context, id: String) {
    val sharedPreference = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString(profileId, id)
    editor.apply()
}

fun getProfileId(context: Context): String? {
    return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
        .getString(profileId, null)
}

fun saveAccessDriverId(context: Context, driverId: Int) {
    val sharedPreference = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putInt(driverIdKey, driverId)
    editor.apply()
}

fun getAccessToken(context: Context): String? {
    return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
        .getString(authTokenKey, null)
}

fun getDriverId(context: Context): Int {
    return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getInt(driverIdKey, 0)
}

fun saveSelectedCompany(context: Context, companyCode: String, companyId: Int) {
    val sharedPreference = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putInt(companyIdKey, companyId)
    editor.putString(companyCodeKey, companyCode)
    editor.apply()
}

fun getSelectedCompanyCode(context: Context): String? {
    return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getString(companyCodeKey, null)
}

fun getSelectedCompanyId(context: Context): Int {
    return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getInt(companyIdKey, -1)
}

fun removeCompanySelection(context: Context) {
    val editor: SharedPreferences.Editor =
        context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).edit()
    editor.remove(companyCodeKey)
    editor.remove(companyIdKey)
    editor.apply()
}

fun clearSession(context: Context) {
    var sharedPreference = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
    Log.d("Clear", "clearSession: ")

    val uid = sharedPreference.getString(userId, "")

    val editor = sharedPreference.edit()

    editor.clear()
    editor.apply()

    if (uid != "") {
        editor.putString(userId, uid)
        Log.d("Driver Found", "clearSession: $uid not deleted saved")
        editor.apply()
    }
}

fun saveUserId(uid:String, context:Context){
    val sharedPreference = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString(userId, uid)
    editor.apply()
}

fun getUserId(context: Context): String? {
    return context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getString(userId, null)
}
