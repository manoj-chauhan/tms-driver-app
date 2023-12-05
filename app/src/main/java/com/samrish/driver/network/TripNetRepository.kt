package com.samrish.driver.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.errormgmt.ErrManager
import com.samrish.driver.models.ActiveStatusDetail
import com.samrish.driver.models.Documents
import com.samrish.driver.models.History
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.TripCheckInRequest
import com.samrish.driver.models.TripDetail
import com.samrish.driver.models.TripRequest
import com.samrish.driver.models.TripStartRequest
import com.samrish.driver.ui.viewmodels.TripHistory
import com.samrish.driver.ui.viewmodels.TripsAssigned
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class TripNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun fetchActiveTrips(): List<TripsAssigned>? {
        val assignedTripType =
            Types.newParameterizedType(MutableList::class.java, TripsAssigned::class.java)
        val adapter: JsonAdapter<MutableList<TripsAssigned>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.url_trips_list)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                Log.d("TAG", "tripList:$result ")
                result.fold(
                    {

                    },
                    {
                        EventBus.getDefault().post("AUTH_FAILED")
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchTripHistory(tripCode: String, operatorId: Int): List<TripHistory>? {
        val assignedTripHistory =
            Types.newParameterizedType(MutableList::class.java, TripHistory::class.java)
        val adapter: JsonAdapter<MutableList<TripHistory>> =
            Moshi.Builder().build().adapter(assignedTripHistory)

        val tripHistoryUrl =
            context.resources.getString(R.string.url_trips_detail) + "$tripCode/" + "history"
        Log.d("tripHistoryurl", "fetchTripHistory: $tripHistoryUrl ")
        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripHistoryUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(adapter))
                Log.d("TAG", "tripList:$result ")
                result.fold(
                    {

                    },
                    {
                        val errorResponse = it.response.responseMessage
                        Log.d("TAG", "fetchTripHistory: $errorResponse")
                        EventBus.getDefault().post("AUTH_FAILED")
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }

    }

    fun fetchPastTrips(currentPage: Int): List<History>? {
        val url =
            context.resources.getString(R.string.url_trip_history) + "assignmentHistory?pageno=" + currentPage
        Log.d("TAG", "fetchHistoryDetail:$url ")

        val historyList = Types.newParameterizedType(MutableList::class.java, History::class.java)
        val history: JsonAdapter<MutableList<History>> =
            Moshi.Builder().build().adapter(historyList)

        getAccessToken(context)?.let {
            val (request1, response1, result) = url.httpGet()
                .authentication().bearer(it)
                .response(moshiDeserializerOf(history))


            result.fold(
                {
//                        historyDetail -> channel4.send(historyDetail)
                },
                { error ->
                    Log.e(
                        "Fuel",
                        "Error $error"
                    )
                }

            )
            return result.get()
        }
        return null;
    }


    fun fetchTripDocuments(tripId: Int, operatorId: Int): MutableList<Documents>? {
        val documentsurl = context.resources.getString(R.string.url_trip_document) + tripId

        val documentList =
            Types.newParameterizedType(MutableList::class.java, Documents::class.java)
        val documents: JsonAdapter<MutableList<Documents>> =
            Moshi.Builder().build().adapter(documentList)

        return try {
            getAccessToken(context)?.let {
                val (request1, response1, result) = documentsurl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .response(moshiDeserializerOf(documents))

                Log.d("TAG", "fetchAssignmentDetail: $request1")
                result.fold(
                    {

                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchTripDetail(tripCode: String, operatorId: Int): TripDetail {
        val tripDetailUrl = context.resources.getString(R.string.url_trips_detail) + tripCode

        return try {
            getAccessToken(context)?.let {
                val (request1, response1, result1) = tripDetailUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(TripDetail::class.java))

                result1.fold(
                    { tripDetail ->
                        tripDetail
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                        throw Exception("Error fetching trip details")
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

    fun fetchTripSchedule(tripCode: String, operatorId: Int): Schedule {
        val tripScheduleUrl =
            context.resources.getString(R.string.url_trip_schedules) + tripCode + "/schedule"

        return try {
            getAccessToken(context)?.let {
                val (request1, response1, result1) = tripScheduleUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(Schedule::class.java))

                result1.fold(
                    { activeDetail ->
                        activeDetail
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                        throw Exception("Error fetching trip details")
                    }
                )
            } ?: throw Exception("Access token is null")
        } catch (e: Exception) {
            Log.e(
                "Fuel",
                "Exception $e"
            )
            throw Exception("Exception fetching trip details")
        }
    }


    fun fetchTripStatus(tripId: Int, operatorId: Int): ActiveStatusDetail {
        val activeStatusUrl =
            context.resources.getString(R.string.url_trip_actions) + tripId + "/activeStatus"

        return try {
            getAccessToken(context)?.let {
                val (request1, response1, result1) = activeStatusUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(ActiveStatusDetail::class.java))

                result1.fold(
                    { activeDetail ->
                        activeDetail
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                        throw Exception("Error fetching trip details")
                    }
                )
            } ?: throw Exception("Access token is null")
        } catch (e: Exception) {
            Log.e(
                "Fuel",
                "Exception $e"
            )
            throw Exception("Exception fetching trip details")
        }
    }

    fun startTrip(tripCode: String, operatorId: Int, deviceIdentifier: String) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val request = TripStartRequest(deviceIdentifier, tripCode)

            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<TripStartRequest> =
                moshi.adapter(TripStartRequest::class.java)
            val requestBody: String = jsonAdapter.toJson(request)
            Log.d("TAG", "startTrip ")
            val url = context.resources.getString(R.string.url_trip_start)
            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                    .response()

                Log.d("TAG", "startTrip: $result")

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
                                    Toast.makeText(context, "Trip not started", Toast.LENGTH_SHORT)
                                }
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)
                            Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                            coroutineScope.launch(Dispatchers.Main) {
                                errorManager.handleErrorResponse(context, errorResponse)
                            }
                        }
                    )
                }
            }
        } catch (e: Exception) {
        }
    }

    fun checkInTrip(placeCode: String, tripCode: String, operatorId: Int) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val checkInRequest = TripCheckInRequest(placeCode, tripCode)

            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<TripCheckInRequest> =
                moshi.adapter(TripCheckInRequest::class.java)
            val requestBody = jsonAdapter.toJson(checkInRequest)

            val url = context.resources.getString(R.string.url_trip_check_in)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
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
                                    Toast.makeText(context, "Trip not checked in", Toast.LENGTH_SHORT)
                                }
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)
                            Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                            coroutineScope.launch(Dispatchers.Main) {
                                errorManager.handleErrorResponse(context, errorResponse)
                            }
                        }
                    )
                }
            }

        } catch (e: Exception) {

        }

    }

    fun departTrip(tripCode: String, operatorId: Int) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val departRequest = TripRequest(tripCode, operatorId)
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<TripRequest> = moshi.adapter(TripRequest::class.java)
            val requestBody = jsonAdapter.toJson(departRequest)


            val url = context.resources.getString(R.string.url_trip_depart)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
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
                                    Toast.makeText(context, "Trip Depart not Completed", Toast.LENGTH_SHORT)
                                }
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)
                            Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                            coroutineScope.launch(Dispatchers.Main) {
                                errorManager.handleErrorResponse(context, errorResponse)
                            }
                        }
                    )
                }
            }

        } catch (e: Exception) {

        }

    }

    fun cancelTrip(tripCode: String, operatorId: Int, navController: NavHostController) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val cancelRequest = TripRequest(tripCode, operatorId)
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<TripRequest> = moshi.adapter(TripRequest::class.java)
            val requestBody = jsonAdapter.toJson(cancelRequest)

            val url = context.resources.getString(R.string.url_trip_cancel)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                    .response()

                if (response.statusCode == 200) {
                    // The request was successful, handle the response here
                    coroutineScope.launch(Dispatchers.Main) {
                        navController.navigate("home")
                        Toast.makeText(context, "Trip Cancelled Successfully", Toast.LENGTH_SHORT).show()
                    }
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
                                    Toast.makeText(context, "Trip not cancelled", Toast.LENGTH_SHORT)
                                }
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)
                            Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                            coroutineScope.launch(Dispatchers.Main) {
                                errorManager.handleErrorResponse(context, errorResponse)
                            }
                        }
                    )
                }
            }


        } catch (e: Exception) {

        }
    }

    fun endTrip(tripCode: String, operatorId: Int, navController: NavHostController) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        try {
            val endRequest = TripRequest(tripCode, operatorId)
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<TripRequest> = moshi.adapter(TripRequest::class.java)
            val requestBody = jsonAdapter.toJson(endRequest)

            val url = context.resources.getString(R.string.url_trip_end)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                    .response()

                if (response.statusCode == 200) {
                    // The request was successful, handle the response here
                    coroutineScope.launch(Dispatchers.Main) {
                        navController.navigate("home")
                        Toast.makeText(context, "Trip Ended Successfully", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    result.fold(
                        { _ ->
                        },
                        { error ->
                            if (error.response.statusCode == 401) {
                                errorManager.getErrorDescription(context)
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)
                            Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                            if(error.response.statusCode == 500) {
                                coroutineScope.launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Trip not  ended", Toast.LENGTH_SHORT)
                                }
                            }
                            coroutineScope.launch(Dispatchers.Main) {
                                errorManager.handleErrorResponse(context, errorResponse)
                            }
                        }
                    )
                }
            }

            Toast.makeText(
                context,
                "TRIP ENDED",
                Toast.LENGTH_SHORT
            ).show()

        } catch(e:Exception){

        }
    }
}


