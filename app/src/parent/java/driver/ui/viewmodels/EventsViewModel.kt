package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.EventManagement.EventManager
import driver.models.Events
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventManager: EventManager): ViewModel() {

    private val  _eventsList: MutableStateFlow<List<Events>?> = MutableStateFlow(null)
    val eventList: StateFlow<List<Events>?> = _eventsList.asStateFlow()

    fun getAllEvents(){
        viewModelScope.launch {
            val events = eventManager.getAllEvents()
            _eventsList.update {
                events
            }
        }
    }

}