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

data class AssignmentDetailData (
    var tripDetail: TripDetail
)

class AssignmentDetailViewModel : ViewModel() {

    private val _assignmentDetail: MutableStateFlow<AssignmentDetailData?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<AssignmentDetailData?> = _assignmentDetail.asStateFlow()

    fun fetchAssignmentDetail(context: Context, tripCode:String, operatorId:Int) {

        val channel1 = Channel<TripDetail>()
//        val channel2 = Channel<MutableList<TripsAssigned>>()

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

//        viewModelScope.launch(Dispatchers.IO) {
//            val tripAssignmentUrl = context.resources.getString(R.string.url_trips_list)
//            getAccessToken(context)?.let {
//
//                val assignedTripType = Types.newParameterizedType(MutableList::class.java, TripsAssigned::class.java)
//                val adapter: JsonAdapter<MutableList<TripsAssigned>> = Moshi.Builder().build().adapter(assignedTripType)
//
//
//                val (request1, response1, result1) = tripAssignmentUrl.httpGet()
//                    .authentication().bearer(it)
//                    .responseObject(moshiDeserializerOf(adapter))
//
//                result1.fold(
//                    {
//                        tripAssignments -> channel2.send(tripAssignments)
//                    },
//                    { error ->
//                        Log.e(
//                            "Fuel",
//                            "Error $error"
//                        )
//                    }
//                )
//            }
//        }
//
        viewModelScope.launch(Dispatchers.IO) {
            val tripDetail = channel1.receive();
//            val tripsAssignment = channel2.receive();
            _assignmentDetail.update { _ ->
                AssignmentDetailData(
                    tripDetail
                )
            }
            Log.i("Fuel", "Response1: $tripDetail")
//            Log.i("Fuel", "Response2: $tripsAssignment")

            Log.i("Fuel", "All Coroutines Finished!")
        }
    }

}