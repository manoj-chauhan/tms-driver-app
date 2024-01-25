package driver.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.models.ParentTrip
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


    fun fetchParentTrip(context: Context){

        viewModelScope.launch(Dispatchers.IO) {
            val tripList = parentTripManager.getActiveTrips()
            _parentTrip.update { _ ->
                tripList
            }
            Log.d("TAG", "fetchTripHistoryDetail: $tripList ")
        }

    }
    
}