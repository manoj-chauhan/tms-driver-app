package driver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.EventManagement.EventManager
import driver.models.Event
import driver.models.EventRegistration
import driver.models.ImagesInfo
import driver.postUploadManagement.PostUploadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventManager: EventManager,private val postUploadManager: PostUploadManager) : ViewModel() {

    private val _eventsList: MutableStateFlow<List<Event>?> = MutableStateFlow(null)
    val eventList: StateFlow<List<Event>?> = _eventsList.asStateFlow()

    private val _eventDetail: MutableStateFlow<Event?> = MutableStateFlow(null)
    val eventDetail: StateFlow<Event?> = _eventDetail.asStateFlow()



    fun getAllEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            val events = eventManager.getAllEvents()
            _eventsList.update {
                events
            }
        }
    }
    private val _uploadedPosts: MutableStateFlow<Pair<String, String>?> = MutableStateFlow(null)
    val postDetails: StateFlow<Pair<String, String>?> = _uploadedPosts.asStateFlow()
    fun uploadPosts(image: ByteArray?, mimeType: String?){
        CoroutineScope(Dispatchers.IO).launch {
            try {

                if (image != null  && mimeType != null) {
                    val postResponse = postUploadManager.uploadPosts(image, mimeType)

                    _uploadedPosts.update {
                        postResponse to mimeType
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }


    fun getEventById(eventId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val event = eventManager.getEventById(eventId)
                _eventDetail.value = event
            } catch (e: Exception) {

                Log.d("event by id error", "getEventById: error in getting eventByid")
            }
        }
    }


    fun addEvents(
        title: String,
        description: String,
        latitude: String,
        longitude: String,
        placeName: String,
        scope: String,
        dateOfEvent: String?,
        timeOfEvent: String?,
        coverImage: ImagesInfo?,
        descriptionImage: List<ImagesInfo?>,
        institute: String,
        profileId: String
    ) {
//        val location = locationInfo(latitude, longitude)
        val eventRegistration= EventRegistration(title, description, placeName, scope, dateOfEvent, timeOfEvent, coverImage, descriptionImage, institute)
        CoroutineScope(Dispatchers.IO).launch {
            eventManager.addEvent(eventRegistration, profileId)
        }
    }

}