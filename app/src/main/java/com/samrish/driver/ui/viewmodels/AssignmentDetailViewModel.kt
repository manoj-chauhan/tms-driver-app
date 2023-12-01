package com.samrish.driver.ui.viewmodels

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
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
import com.samrish.driver.tripmgmt.TripManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
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
class AssignmentDetailViewModel @Inject constructor(private  val errorManager: ErrManager, application: Application, private val tripManager: TripManager): AndroidViewModel(application)  {

    private val _assignmentDetail: MutableStateFlow<AssignmentDetailData?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<AssignmentDetailData?> = _assignmentDetail.asStateFlow()

    fun fetchAssignmentDetail(context: Context, tripId:Int, tripCode:String, operatorId:Int) {

        Log.i("","$tripId")

        val channel1 = Channel<TripDetail>()
        val channel2 = Channel<ActiveStatusDetail>()
        val channel3 = Channel<Schedule>()

        var document: MutableList<Documents>? = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            document = tripManager.getDocuments(tripId, operatorId)
        }


        viewModelScope.launch(Dispatchers.IO) {
            val tripDetail = tripManager.getTripDetail(tripCode, operatorId)
            channel1.send(tripDetail)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val activeStatus = tripManager.getTripStatus(tripId, operatorId)
            channel2.send(activeStatus)
        }


        viewModelScope.launch(Dispatchers.IO) {
            val schedule = tripManager.getTripSchedule(tripCode, operatorId)
            channel3.send(schedule)
        }



        viewModelScope.launch(Dispatchers.IO) {

            val tripDetail = channel1.receive()
            val statusDetail = channel2.receive();
            val schedule = channel3.receive()


            _assignmentDetail.update { _ ->
                AssignmentDetailData(
                     tripDetail,
                     statusDetail,
                     schedule,
                     document,
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
                                if(error.response.statusCode == 500) {
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "Trip not started", Toast.LENGTH_SHORT)
                                    }
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
                                if(error.response.statusCode == 500) {
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "Trip not checked in", Toast.LENGTH_SHORT)
                                    }
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

                                if(error.response.statusCode == 500) {
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "Trip Depart not Completed", Toast.LENGTH_SHORT)
                                    }
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

    fun cancelTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int,  navController: NavHostController) {
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
                        launch(Dispatchers.Main) {
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
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "Trip not cancelled", Toast.LENGTH_SHORT)
                                    }
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

    fun endTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int,  navController: NavHostController) {
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
                                launch(Dispatchers.Main) {
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
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "Trip not  ended", Toast.LENGTH_SHORT)
                                    }
                                }
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