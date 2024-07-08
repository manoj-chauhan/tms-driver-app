package com.drishto.driver.errormgmt

import android.content.Context

interface ErrManager {

    fun getErrorDescription(context: Context)

    fun getErrorDescription500(context: Context, error: String)

    fun handleErrorResponse(context: Context, error: String)

    fun getErrorDescription401(context: Context, error: String)
     fun getErrorDescription403(context: Context, error: String)
     fun getErrorDescription404(context: Context, errorResponse: String)
     fun getErrorDescription400(context: Context, errorResponse: String)

    fun getErrorRouteDescription401(context: Context, errorResponse: String):String
    fun getErrorRouteDescription404(context: Context, errorResponse: String):String
    fun getErrorRouteDescription500(context: Context, errorResponse: String):String
    fun getErrorRouteDescription403(context: Context, errorResponse: String):String
    fun getErrorRouteDescription400(context: Context, errorResponse: String):String

}