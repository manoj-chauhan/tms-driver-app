package com.drishto.driver.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.Student
import com.drishto.driver.models.User
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class UserNetRepository  @Inject constructor(@ApplicationContext private val context: Context, private val errorManager:ErrManager) {

    fun editUserName(name: String) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val request = User(name)

            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<User> =
                moshi.adapter(User::class.java)
            val requestBody: String = jsonAdapter.toJson(request)

            val url = context.resources.getString(R.string.edit_user_name) + "?name=" + name
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
                            if (error.response.statusCode == 401 ) {
                                errorManager.getErrorDescription(context)
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)

                            if (error.response.statusCode == 403 ) {
                                errorManager.getErrorDescription403(context, errorResponse)
                            }

                            if (error.response.statusCode == 404 ) {
                                errorManager.getErrorDescription404(context, "No url found")
                            }

                            if(error.response.statusCode == 500){
                                errorManager.getErrorDescription500(context, "Something Went Wrong")
                            }

                        }
                    )
                }
            }
        } catch (e: Exception) {
        }

    }

    fun getChildrenList(): List<Student>? {
        val assignedTripType =
            Types.newParameterizedType(MutableList::class.java, Student::class.java)
        val adapter: JsonAdapter<MutableList<Student>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.children_list)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {

                    },
                    {error->
                        EventBus.getDefault().post("AUTH_FAILED")
                        if (error.response.statusCode == 401 ) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)

                        if (error.response.statusCode == 403 ) {
                            errorManager.getErrorDescription403(context, errorResponse)
                        }

                        if (error.response.statusCode == 404 ) {
                            errorManager.getErrorDescription404(context, "No url found")
                        }

                        if(error.response.statusCode == 500){
                            errorManager.getErrorDescription500(context, "Something Went Wrong")
                        }
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun uploadProfileImage(image: ByteArray, userId:Int) {
        Log.d("Upload", "uploadProfileImage: $image")
        try {
            val url = context.resources.getString(R.string.upload_photo) + userId + "/profile-photo"
            Log.d("upload", "uploadProfileImage: $url")
            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .body(image)
                    .response()

                if (response.statusCode == 200) {
                    Log.d("Funciton", "uploadProfileImage: ")
                } else {
                    result.fold(
                        { _ ->

                        },
                        { error ->
                            if (error.response.statusCode == 401 ) {
                                errorManager.getErrorDescription(context)
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)

                            if (error.response.statusCode == 403 ) {
                                errorManager.getErrorDescription403(context, errorResponse)
                            }

                            if (error.response.statusCode == 404 ) {
                                errorManager.getErrorDescription404(context, "No url found")
                            }

                            if(error.response.statusCode == 500){
                                errorManager.getErrorDescription500(context, "Something Went Wrong")
                            }
                        }
                    )
                }
            }
        } catch (e: Exception) {
        }
    }

    fun getUploadedImage(userId:Int): Bitmap? {
        val url = context.resources.getString(R.string.upload_photo) + userId + "/profile-photo"
        Log.d("TAG", "getUploadedImage: $url")

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = url.httpGet()
                    .authentication().bearer(it)
                    .response()

                result.fold(
                    { data ->
                        BitmapFactory.decodeByteArray(data, 0, data.size)
                    },
                    {error->
                        if (error.response.statusCode == 401 ) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)

                        if (error.response.statusCode == 403 ) {
                            errorManager.getErrorDescription403(context, errorResponse)
                        }

                        if(error.response.statusCode == 500){
                            errorManager.getErrorDescription500(context, "Something Went Wrong")
                        }
                        null
                    }
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}


