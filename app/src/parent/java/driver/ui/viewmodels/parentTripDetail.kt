package driver.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.models.ParentTripDetail
import driver.models.loaderMap
import driver.models.point
import driver.tripManagement.ParentTripManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class parentTripDetail @Inject constructor(private val parentTripManager: ParentTripManager) : ViewModel() {
    private val _assignmentDetail: MutableStateFlow<ParentTripDetail?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<ParentTripDetail?> = _assignmentDetail.asStateFlow()

    private val _currentDriver: MutableStateFlow<loaderMap?> = MutableStateFlow(null)
    val currentDriver: StateFlow<loaderMap?> = _currentDriver.asStateFlow()

    private val  _points: MutableStateFlow<List<point>?> = MutableStateFlow(null)
    val points: StateFlow<List<point>?> = _points.asStateFlow()


    fun fetchTripRouteCoordinates(passengerTripId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val pointList = parentTripManager.getTripLatLon(passengerTripId)
            _points.update { _ ->
                pointList
            }
        }
    }

    fun fetchTripDetails(context: Context, passengerTripId:Int, navHostController: NavHostController){
        viewModelScope.launch(Dispatchers.IO) {
            val tripDetail = parentTripManager.getTripDetail(passengerTripId, navHostController)
            if (tripDetail != null) {
                _assignmentDetail.update { _ ->
                    tripDetail
                }
            } else {

            }
        }

    }

    fun fetchDriverLocation(passengerTripId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val driverLoc = parentTripManager.getDriverLoc(passengerTripId)
            if (driverLoc!=null) {
                _currentDriver.update { _ ->
                    loaderMap(
                        driverLoc,
                        true
                    )
                }
            }else{

            }
        }
    }

    fun reload(passengerTripId: Int){
            fetchDriverLocation(passengerTripId)
    }


}