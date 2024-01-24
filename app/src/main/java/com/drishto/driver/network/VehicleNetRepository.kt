package com.drishto.driver.network

import android.content.Context
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.drishto.driver.R
import com.squareup.moshi.JsonClass
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class AssignmentCodeResponse(
    var assignmentCode:String
)


class VehicleNetRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun generateAssignmentCode(): String? {
        val vehicleAssignmentUrl = context.resources.getString(R.string.url_driver_code)
        getAccessToken(context)?.let {
            val (request1, response1, result) = vehicleAssignmentUrl.httpGet()
                .authentication().bearer(it)
                .responseObject(moshiDeserializerOf(AssignmentCodeResponse::class.java))
            return result.get().assignmentCode
        }

        return null
    }
}