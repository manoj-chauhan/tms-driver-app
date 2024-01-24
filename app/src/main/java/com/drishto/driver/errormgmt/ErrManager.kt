package com.drishto.driver.errormgmt

import android.content.Context

interface ErrManager {

    fun getErrorDescription(context: Context)
    fun handleErrorResponse(context: Context, error: String)
}