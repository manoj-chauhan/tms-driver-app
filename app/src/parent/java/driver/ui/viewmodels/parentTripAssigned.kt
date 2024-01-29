package driver.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.models.ParentTrip
import com.drishto.driver.models.point
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.tripManagement.ParentTripManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class parentTripAssigned @Inject constructor(private val parentTripManager: ParentTripManager) : ViewModel()  {
    private val  _parentTrip: MutableStateFlow<List<ParentTrip>?> = MutableStateFlow(null)
    val parentTrip: StateFlow<List<ParentTrip>?> = _parentTrip.asStateFlow()

    private val  _points: MutableStateFlow<List<point>?> = MutableStateFlow(null)
    val points: StateFlow<List<point>?> = _points.asStateFlow()

    fun fetchParentTrip(context: Context){

        viewModelScope.launch(Dispatchers.IO) {
            val tripList = parentTripManager.getActiveTrips()
            _parentTrip.update { _ ->
                tripList
            }
            Log.d("TAG", "fetchTripHistoryDetail: $tripList ")
        }

    }

    fun fetchTripRouteCoordinates(context: Context, operatorId:Int, tripCode:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val pointList = parentTripManager.getTripLatLon(operatorId, tripCode)
            _points.update { _ ->
                pointList
            }
        }
    }

}