package driver.EventManagement

import driver.models.EventRegistration
import driver.models.Events
import driver.network.EventNetRepository
import javax.inject.Inject

class EventManagerImpl @Inject constructor(
    private val eventNetRepository: EventNetRepository,

) : EventManager {

    override suspend fun getAllEvents(): List<Events>? {
        return eventNetRepository.getAllEvents()
    }

    override suspend fun addEvent(event: EventRegistration, profileId: String) {
        return eventNetRepository.addEvent(event, profileId)
    }
}