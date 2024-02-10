package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.models.PlaceInfo
import driver.tripManagement.ParentTripManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class placeDetailViewModel @Inject constructor(private val parentTripManager: ParentTripManager) : ViewModel() {
    private val _placeInfo: MutableStateFlow<PlaceInfo?> = MutableStateFlow(null)
    val placeInfo: StateFlow<PlaceInfo?> = _placeInfo.asStateFlow()
    fun fetchPlaceCoordinates(placeCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val coordinates = parentTripManager.getPlaceLatLng(placeCode)
            _placeInfo.update {
                coordinates
            }
        }

    }
}