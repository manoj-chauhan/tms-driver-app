package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.services.getAccessToken
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@JsonClass(generateAdapter = true)
data class TripDetail(
    var status : String,
    var tripId : Int,
    var tripCode : String,
    var tripName : String,
    var createdBy : String,
    var createdAt : String,
    var updatedBy : String,
    var lastUpdatedAt : String,
    var companyId : Int,
    var companyName : String,
    var operatorId : Int,
    var operatorName : String,
    var approvalStatus : String,
    var label : String,
    var routeId : Int,
    var tripDate : String,
    var tripTime : String,
)

@JsonClass(generateAdapter = true)
data class ActiveStatusDetail(
    var driverId : Int,
    var driverName : String,
    var actions : MutableList<String>
)


data class AssignmentDetailData (
    var tripDetail: TripDetail,
    var activeStatusDetail: ActiveStatusDetail
)

class AssignmentDetailViewModel : ViewModel() {

    private val _assignmentDetail: MutableStateFlow<AssignmentDetailData?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<AssignmentDetailData?> = _assignmentDetail.asStateFlow()

    fun fetchAssignmentDetail(context: Context, tripId:Int, tripCode:String, operatorId:Int) {

        val channel1 = Channel<TripDetail>()
        val channel2 = Channel<ActiveStatusDetail>()

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
            val tripDetail = channel1.receive();
            val statusDetail = channel2.receive();
            _assignmentDetail.update { _ ->
                AssignmentDetailData(
                    tripDetail,
                    activeStatusDetail = statusDetail
                )
            }
            Log.i("Fuel", "Response1: $tripDetail")
            Log.i("Fuel", "Response2: $statusDetail")
        }
    }

}