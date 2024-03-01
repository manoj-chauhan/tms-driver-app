package driver.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.DriverPlans
import com.drishto.driver.network.getAccessToken
import com.drishto.driver.vehiclemgmt.VehicleManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
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
data class errorDescription(
    val  errorCode :  String,
    val errorDescription:String,
    val errorShortDescription: String
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
    var vehicles: MutableList<VehicleAssignment>,
    var assignmentCode: String = "Not Available",
    var isAssignmentCodeVisible: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val vehicleManager: VehicleManager, private  val errorManager: ErrManager) : ViewModel() {
    private val  _driverPlan: MutableStateFlow<List<DriverPlans>?> = MutableStateFlow(null)
    val driverPlan: StateFlow<List<DriverPlans>?> = _driverPlan.asStateFlow()

    private val _currentAssignment: MutableStateFlow<CurrentAssignmentData?> =
        MutableStateFlow(null)
    val currentAssignment: StateFlow<CurrentAssignmentData?> = _currentAssignment.asStateFlow()


    fun fetchAssignmentDetail(context: Context) {

        val channel1 = Channel<MutableList<VehicleAssignment>>()
        val channel2 = Channel<MutableList<TripsAssigned>>()

        viewModelScope.launch(Dispatchers.IO) {
            val vehicleAssignmentUrl = context.resources.getString(R.string.url_vehicle_assignment)
            getAccessToken(context)?.let {

                val assignedVehicleType =
                    Types.newParameterizedType(MutableList::class.java, VehicleAssignment::class.java)
                val adapter: JsonAdapter<MutableList<VehicleAssignment>> =
                    Moshi.Builder().build().adapter(assignedVehicleType)

                val (request1, response1, result1) = vehicleAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))

                result1.fold(
                    { vehicleAssignments ->
                        channel1.send(vehicleAssignments)
                    },
                    { error ->
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription(context)
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
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
//                        Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                        launch(Dispatchers.Main) {
                            errorManager.handleErrorResponse(context, errorResponse)
                        }
                    }
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val vehicleAssignments = channel1.receive();
            val tripsAssignments = channel2.receive();

            _currentAssignment.update { old ->
                CurrentAssignmentData(
                    userLocationVisible = old?.userLocationVisible ?: false,
                    trips = tripsAssignments,
                    vehicles = vehicleAssignments,
                    assignmentCode = old?.assignmentCode?: "Not Available",
                    isAssignmentCodeVisible = old?.isAssignmentCodeVisible?:false
                )
            }
        }
    }

    fun driverPlanAssignment(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val planList = vehicleManager.getDriverPlan(context)
            _driverPlan.update {
                planList
            }
        }
    }


    fun generateAssignmentCode(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            var code = vehicleManager.getAssignmentCode()
            _currentAssignment.update { old ->
                old?.let {
                    CurrentAssignmentData(
                        it.userLocationVisible,
                        it.trips,
                        it.vehicles,
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
                    vehicles = old.vehicles,
                    assignmentCode = "Not Available",
                    isAssignmentCodeVisible = false
                )
            }
        }

    }
}