package com.samrish.driver.ui.viewmodels

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.errormgmt.ErrManager
import com.samrish.driver.models.ActiveStatusDetail
import com.samrish.driver.models.AssignmentDetailData
import com.samrish.driver.models.Documents
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.TripCheckInRequest
import com.samrish.driver.models.TripDetail
import com.samrish.driver.models.TripRequest
import com.samrish.driver.models.TripStartRequest
import com.samrish.driver.network.getAccessToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentDetailViewModel @Inject constructor(private  val errorManager: ErrManager, application: Application): AndroidViewModel(application)  {

    private val _assignmentDetail: MutableStateFlow<AssignmentDetailData?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<AssignmentDetailData?> = _assignmentDetail.asStateFlow()

    fun fetchAssignmentDetail(context: Context, tripId:Int, tripCode:String, operatorId:Int) {

        Log.i("","$tripId")

        val channel1 = Channel<TripDetail>()
        val channel2 = Channel<ActiveStatusDetail>()
        val channel3 = Channel<Schedule>()
        val channel4= Channel<MutableList<Documents>>()


        viewModelScope.launch(Dispatchers.IO) {
            val documentsurl = context.resources.getString(R.string.url_trip_document) + tripId

            val  documentList = Types.newParameterizedType(MutableList::class.java, Documents::class.java)
            val documents: JsonAdapter<MutableList<Documents>> = Moshi.Builder().build().adapter( documentList)
            
            getAccessToken(context)?.let {
                val (request1, response1, result) = documentsurl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .response(moshiDeserializerOf(documents))

                Log.d("TAG", "fetchAssignmentDetail: $request1")
                result.fold(
                    {
                            documentsDetail -> channel4.send(documentsDetail)
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
            }
        }


        viewModelScope.launch(Dispatchers.IO) {
            val tripDetailUrl = context.resources.getString(R.string.url_trips_detail) + tripCode
            getAccessToken(context)?.let {
                val (request1, response1, result1) = tripDetailUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(TripDetail::class.java))

                result1.fold(
                    {
                        tripDetail -> channel1.send(tripDetail)
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {

            val activeStatusUrl = context.resources.getString(R.string.url_trip_actions) + tripId +"/activeStatus"
            getAccessToken(context)?.let {
                val (request1, response1, result1) = activeStatusUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(ActiveStatusDetail::class.java))

                result1.fold(
                    {
                            statusDetail -> channel2.send(statusDetail)
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
            }
        }


        viewModelScope.launch(Dispatchers.IO) {
            val tripScheduleUrl = context.resources.getString(R.string.url_trip_schedules) + tripCode + "/schedule"

            getAccessToken(context)?.let {
                val (request1, response1, result1) = tripScheduleUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(Schedule::class.java))

                result1.fold(
                    {
                            schedule -> channel3.send(schedule)
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
            }
        }



        viewModelScope.launch(Dispatchers.IO) {

            val tripDetail = channel1.receive();
            val statusDetail = channel2.receive();
            val schedule = channel3.receive()
            val documents = channel4.receive()


            _assignmentDetail.update { _ ->
                AssignmentDetailData(
                     tripDetail,
                     statusDetail,
                     schedule,
                     documents,
                    true
                )
            }
            Log.i("Fuel", "Response1: $tripDetail")
            Log.i("Fuel", "Response2: $statusDetail")
            Log.i("Fuel", "Response3: $schedule")
        }
    }
    fun startTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deviceIdentifier = Settings.Secure.getString(
                    getApplication<Application>().contentResolver, Settings.Secure.ANDROID_ID
                )

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

                                val errorResponse = error.response.data.toString(Charsets.UTF_8)
                                Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                                launch(Dispatchers.Main) {
                                    errorManager.handleErrorResponse(context, errorResponse)
                                }
                            }
                        )
                    }
                }
                fetchAssignmentDetail(context,tripId, tripCode, operatorId)

            } catch (e: Exception) {
            }
        }
    }

    fun checkInTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int, placeCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

                                val errorResponse = error.response.data.toString(Charsets.UTF_8)
                                Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                                launch(Dispatchers.Main) {
                                    errorManager.handleErrorResponse(context, errorResponse)
                                }
                            }
                        )
                    }
                }
                fetchAssignmentDetail(context,tripId, tripCode, operatorId)


            } catch (e: Exception) {

            }
        }
    }

    fun departTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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

                                val errorResponse = error.response.data.toString(Charsets.UTF_8)
                                Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                                launch(Dispatchers.Main) {
                                    errorManager.handleErrorResponse(context, errorResponse)
                                }
                            }
                        )
                    }
                }
                fetchAssignmentDetail(context,tripId, tripCode, operatorId)

            } catch (e: Exception) {

            }
        }
    }

    fun cancelTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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
                                launch(Dispatchers.Main) {
                                    errorManager.handleErrorResponse(context, errorResponse)
                                }
                            }
                        )
                    }
                }
                fetchAssignmentDetail(context,tripId, tripCode, operatorId)


            } catch (e: Exception) {

            }
        }
    }

    fun endTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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
                                launch(Dispatchers.Main) {
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

                fetchAssignmentDetail(context,tripId, tripCode, operatorId)
            } catch(e:Exception){

            }
        }
    }

    fun documents(context: Context, tripId: Int){

    }
}