package com.drishto.driver.errormgmt

import android.content.Context

interface ErrManager {

    fun getErrorDescription(context: Context)

    fun getErrorDescription500(context: Context, error: String)

    fun handleErrorResponse(context: Context, error: String)

    fun getErrorDescription401(context: Context, error: String)
     fun getErrorDescription403(context: Context, error: String)
     fun getErrorDescription404(context: Context, errorResponse: String)
}