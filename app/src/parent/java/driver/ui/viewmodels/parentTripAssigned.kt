package driver.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.models.ParentPastTrip
import driver.models.ParentTrip
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




    private val  _pastTrips: MutableStateFlow<List<ParentPastTrip>?> = MutableStateFlow(null)
    val pastTripList: StateFlow<List<ParentPastTrip>?> = _pastTrips.asStateFlow()





    fun fetchParentTrip(context: Context){

        viewModelScope.launch(Dispatchers.IO) {
            val tripList = parentTripManager.getActiveTrips()
            _parentTrip.update { _ ->
                tripList
            }
        }

    }




    fun fetchParentPastTrip() {
        viewModelScope.launch(Dispatchers.IO) {
            val pastTrip = parentTripManager.getPastTrips()
            _pastTrips.update { _ ->
                pastTrip
            }
        }
    }

}