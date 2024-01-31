package com.drishto.driver.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.UserProfile
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserNetRepository  @Inject constructor(@ApplicationContext private val context: Context, private val errorManager:ErrManager) {

    fun editUserName(name: String ){
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val request = UserProfile(name)

            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<UserProfile> =
                moshi.adapter(UserProfile::class.java)
            val requestBody: String = jsonAdapter.toJson(request)

            val url = context.resources.getString(R.string.edit_user_name)+"?name="+ name
            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .jsonBody(requestBody)
                    .response()

                if (response.statusCode == 200) {
                    // The request was successful, handle the response here
                } else {
                    result.fold(
                        { _ ->

                        },
                        { error ->
                            if (error.response.statusCode == 401) {
                                errorManager.getErrorDescription(context)
                            }
                            if(error.response.statusCode == 500) {
                                coroutineScope.launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Can not Chnage Name ", Toast.LENGTH_SHORT)
                                }
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)
                            Log.d("Error", "fetchAssignmentDetail: $errorResponse")

                        }
                    )
                }
            }
        } catch (e: Exception) {
        }

    }
}