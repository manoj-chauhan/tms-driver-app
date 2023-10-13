package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.models.AssignedVehicle
import com.samrish.driver.services.getAccessToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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


class VehicleAssignmentViewModel : ViewModel() {

    private val _currentAssignment: MutableStateFlow<VehicleAssignment?> = MutableStateFlow(null)
    val currentAssignment: StateFlow<VehicleAssignment?> = _currentAssignment.asStateFlow()

    fun fetchAssignmentDetail(context: Context) {
        val routine1 = viewModelScope.launch(Dispatchers.IO) {
            val vehicleAssignmentUrl = context.resources.getString(R.string.url_vehicle_assignment)
            getAccessToken(context)?.let {
                val (request1, response1, result1) = vehicleAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(VehicleAssignment::class.java))

                result1.fold(
                    { vehicleAssignment ->
                        _currentAssignment.update { _ ->
                            ////The below code is not supposed to be here, it is placed here just for testing serialisation using Moshi library
                            val moshi = Moshi.Builder().build()
                            val adapter: JsonAdapter<VehicleAssignment> =
                                moshi.adapter(VehicleAssignment::class.java)
                            val json: String? = adapter.toJson(vehicleAssignment)
                            Log.i("Fuel", "Generated: $json")
                            ////The above code is not supposed to be here, it is placed here just for testing serialisation using Moshi library
                            vehicleAssignment
                        }
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

        val routine2 = viewModelScope.launch(Dispatchers.IO) {
            val tripAssignmentUrl = context.resources.getString(R.string.url_trips_list)
            getAccessToken(context)?.let {

                val assignedTripType = Types.newParameterizedType(MutableList::class.java, TripsAssigned::class.java)
                val adapter: JsonAdapter<List<AssignedVehicle>> = Moshi.Builder().build().adapter(assignedTripType)


                val (request1, response1, result1) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))

                result1.fold(
                    {
                        tripAssignments -> Log.i("Fuel", "$tripAssignments")
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
            routine1.join()
            routine2.join()

            Log.i("Fuel", "All Coroutines Finished!")
        }


    }
}