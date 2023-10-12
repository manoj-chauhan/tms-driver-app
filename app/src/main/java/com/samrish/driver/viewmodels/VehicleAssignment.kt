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
import com.samrish.driver.services.vehicleDetails
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
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

class VehicleAssignmentViewModel : ViewModel() {

    private val _currentAssignment: MutableStateFlow<VehicleAssignment?> = MutableStateFlow(null)
    val currentAssignment: StateFlow<VehicleAssignment?> = _currentAssignment.asStateFlow()

    fun fetchAssignmentDetail(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
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
                            val adapter: JsonAdapter<VehicleAssignment> = moshi.adapter(VehicleAssignment::class.java)
                            val json: String? = adapter.toJson(vehicleAssignment)
                            Log.i("Fuel","Generated: $json")
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
    }
}