package com.drishto.driver.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.ChildrenList
import com.drishto.driver.models.childrenAddPlan
import com.drishto.driver.models.childrenEditPlan
import com.drishto.driver.models.scheduleList
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

class DriverNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun fetchPlanChildren(planId:Int, operatorId: Int): List<ChildrenList>? {
        val assignedTripType =
            Types.newParameterizedType(MutableList::class.java, ChildrenList::class.java)
        val adapter: JsonAdapter<MutableList<ChildrenList>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.url_student_list)+ planId +"/passengers?page_no=0"

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(adapter))
                Log.d("TAG", "tripList:$result ")
                result.fold(
                    {

                    },
                    {error ->
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

    fun fetchSchedules(planId: Int, operatorId: Int): scheduleList{
            val tripDetailUrl = context.resources.getString(R.string.url_schedule_plan) + planId

            return try {
                getAccessToken(context)?.let {
                    val (request1, response1, result1) = tripDetailUrl.httpGet()
                        .authentication().bearer(it)
                        .header("Company-Id", operatorId)
                        .responseObject(moshiDeserializerOf(scheduleList::class.java))

                    result1.fold(
                        { schedule ->
                            schedule
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

                            throw Exception("Error fetching trip schedule" )
                        }
                    )
                }
                    ?: throw Exception("Access token is null") // Throw an exception if access token is null
            } catch (e: Exception) {
                Log.e(
                    "Fuel",
                    "Exception $e"
                )
                throw Exception("Exception fetching trip details")
            }
    }

    fun addStudent(
        name: String,
        schoolName: String,
        primarynumber: String,
        standard: String,
        selectedText: String,
        secondarynumber: String,
        selectedDate: String,
        guardianName: String,
        schoolAddress: String,
        planId: Int,
        boardingPlaceId: Int,
        deboardingPlaceId: Int,
        operatorId: Int,
    ) {

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val studentRequest = childrenAddPlan(name, schoolName,  primarynumber, standard, selectedText, secondarynumber, selectedDate, guardianName, schoolAddress, planId, boardingPlaceId, deboardingPlaceId)
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<childrenAddPlan> = moshi.adapter(childrenAddPlan::class.java)
            val requestBody = jsonAdapter.toJson(studentRequest)

            val url = context.resources.getString(R.string.url_addStudent_plan)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                    .response()

                if (response.statusCode == 200) {
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

            Toast.makeText(
                context,
                "Student Added Successfully",
                Toast.LENGTH_SHORT
            ).show()

        } catch(e:Exception){

        }


    }

    fun editStudent(
        name: String,
        schoolName: String,
        primarynumber: String,
        standard: String,
        selectedText: String,
        secondarynumber: String,
        selectedDate: String,
        guardianName: String,
        schoolAddress: String,
        boardingPlaceId: Int,
        deboardingPlaceId: Int,
        operatorId: Int,
        studentId:Int
    ) {

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val studentRequest = childrenEditPlan(name, schoolName,  primarynumber, standard, selectedText, secondarynumber, selectedDate, guardianName, schoolAddress, boardingPlaceId, deboardingPlaceId)
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<childrenEditPlan> = moshi.adapter(childrenEditPlan::class.java)
            val requestBody = jsonAdapter.toJson(studentRequest)

            val url = context.resources.getString(R.string.url_editStudent_detail)+studentId

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.put(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                    .response()

                if (response.statusCode == 200) {
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

            Toast.makeText(
                context,
                "Student Edited Successfully",
                Toast.LENGTH_SHORT
            ).show()

        } catch(e:Exception){

        }
    }

}