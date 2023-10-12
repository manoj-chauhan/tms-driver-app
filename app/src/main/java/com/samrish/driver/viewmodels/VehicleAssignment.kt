package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.httpGet
import com.samrish.driver.services.vehicleDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VehicleAssignment(
    val vehicleId : Int,
    val vehicleNumber : String,
    val companyId : Int,
    val companyName : String,
    val assignerName : String,
    val assignedAt : String,
    val vehicleSize : Int,
    val model : String,
    val brand : String,
    val fuelType : String
    
    
)

class VehicleAssignmentViewModel : ViewModel() {

    private val _currentAssignment:MutableStateFlow<VehicleAssignment?> = MutableStateFlow(null)
    val currentAssignment: StateFlow<VehicleAssignment?> = _currentAssignment.asStateFlow()

    fun fetchAssignmentDetail(context:Context) {
        Log.i("Fuel", "Going for fuel")
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("Fuel", "Requesting using fuel")
            val (request, response, result) = "https://publicobject.com/helloworld.txt".httpGet().response()
            result.fold(
                {_ -> Log.i("Fuel", "Response + ${response.body().asString("text/plain")}")},
                {error -> Log.e("Fuel", "Error ${error.response.body().asString("text/plain")}")}
            )
            Log.e("Fuel", "End of the call")
        }

        vehicleDetails(context, onVehicleDetailFetched = {
            _currentAssignment.update { assignment ->
                VehicleAssignment(it.vehicleId, it.vehicleNumber, it.companyId, it.companyName, it.assignerName,it.assignedAt, it.vehicleSize, it.model, it.brand, it.fuelType )
            }
        })
    }
}