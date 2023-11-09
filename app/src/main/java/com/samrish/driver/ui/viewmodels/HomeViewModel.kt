package com.samrish.driver.ui.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.LoginActivity
import com.samrish.driver.R
import com.samrish.driver.network.getAccessToken
import com.samrish.driver.vehiclemgmt.VehicleManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
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

@JsonClass(generateAdapter = true)
data class VehicleAssignment(
    val vehicleId: Int,
    val vehicleNumber: String,
    val companyId: Int,
    val companyName: String,
    val assignerName: String,
    val assignedAt: String,
    val vehicleSize: Int,
    val model: String,
    val brand: String,
    val fuelType: String
)

@JsonClass(generateAdapter = true)
data class TripsAssigned(
    var tripCode: String,
    var tripName:String,
    var status: String,
    var label: String,
    var companyName: String,
    var companyCode: String,
    var operatorCompanyName: String,
    var operatorCompanyCode: String,
    var operatorCompanyId: Int,
    var tripDate: String,
    var tripId: Int
)

data class CurrentAssignmentData (
    var userLocationVisible: Boolean,
    var trips: MutableList<TripsAssigned>,
    var vehicle: VehicleAssignment,
    var assignmentCode: String = "Not Available",
    var isAssignmentCodeVisible: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val vehicleManager: VehicleManager) : ViewModel() {

    private val _currentAssignment: MutableStateFlow<CurrentAssignmentData?> =
        MutableStateFlow(null)
    val currentAssignment: StateFlow<CurrentAssignmentData?> = _currentAssignment.asStateFlow()


    fun fetchAssignmentDetail(context: Context) {

        val channel1 = Channel<VehicleAssignment>()
        val channel2 = Channel<MutableList<TripsAssigned>>()

        viewModelScope.launch(Dispatchers.IO) {
            val vehicleAssignmentUrl = context.resources.getString(R.string.url_vehicle_assignment)
            getAccessToken(context)?.let {
                val (request1, response1, result1) = vehicleAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(VehicleAssignment::class.java))

                result1.fold(
                    { vehicleAssignment ->
                        channel1.send(vehicleAssignment)
                    },
                    { error ->
                        if (error.response.statusCode == 401) {
                            navigateToLoginActivity(context)
                        }
                    }
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val tripAssignmentUrl = context.resources.getString(R.string.url_trips_list)
            getAccessToken(context)?.let {

                val assignedTripType =
                    Types.newParameterizedType(MutableList::class.java, TripsAssigned::class.java)
                val adapter: JsonAdapter<MutableList<TripsAssigned>> =
                    Moshi.Builder().build().adapter(assignedTripType)


                val (request1, response1, result1) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))

                result1.fold(
                    { tripAssignments ->
                        channel2.send(tripAssignments)

                    },
                    { error ->
                        if (error.response.statusCode == 401) {
                            navigateToLoginActivity(context)
                        }
                    }
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val vehicleAssignment = channel1.receive();
            val tripsAssignment = channel2.receive();
            _currentAssignment.update { old ->
                CurrentAssignmentData(
                    userLocationVisible = old?.userLocationVisible ?: false,
                    trips = tripsAssignment,
                    vehicle = vehicleAssignment,
                    assignmentCode = old?.assignmentCode?: "Not Available",
                    isAssignmentCodeVisible = old?.isAssignmentCodeVisible?:false
                )
            }
        }
    }

    private fun navigateToLoginActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
    }


    fun generateAssignmentCode(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            var code = vehicleManager.getAssignmentCode()
            _currentAssignment.update { old ->
                old?.let {
                    CurrentAssignmentData(
                        it.userLocationVisible,
                        it.trips,
                        it.vehicle,
                        code,
                        isAssignmentCodeVisible = true
                    )
                }
            }
        }
    }

    fun hideAssignmentCode(context: Context) {
        _currentAssignment.update { old ->
            old?.let {
                CurrentAssignmentData(
                    userLocationVisible = old.userLocationVisible,
                    trips = old.trips,
                    vehicle = old.vehicle,
                    assignmentCode = "Not Available",
                    isAssignmentCodeVisible = false
                )
            }
        }

    }
}