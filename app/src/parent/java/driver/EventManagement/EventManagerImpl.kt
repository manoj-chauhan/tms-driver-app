package driver.EventManagement

import driver.models.Event
import driver.models.EventRegistration
import driver.network.EventNetRepository
import javax.inject.Inject

class EventManagerImpl @Inject constructor(
    private val eventNetRepository: EventNetRepository,

) : EventManager {

    override suspend fun getAllEvents(): List<Event>? {
        return eventNetRepository.getAllEvents()
    }

    override suspend fun addEvent(event: EventRegistration, profileId: String) {
        return eventNetRepository.addEvent(event, profileId)
    }

    override  suspend fun getEventById(eventId:String):Event?{
        return eventNetRepository.getEventById(eventId)
    }
}