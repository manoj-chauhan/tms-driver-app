package com.samrish.driver.network

import android.content.Context
import android.content.SharedPreferences


private val authStorage = "AUTHENTICATION"
private val authTokenKey = "AUTH_TOKEN"
private val driverIdKey = "DRIVER_ID"
private val companyCodeKey = "COMPANY_CODE"
private val companyIdKey = "COMPANY_ID"

fun saveAccessToken(context: Context, token: String) {
    val sharedPreference = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString(authTokenKey, token)
    editor.apply()
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
    var editor = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).edit()
    editor.clear()
    editor.apply()
}
