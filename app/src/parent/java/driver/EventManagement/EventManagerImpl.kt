package driver.EventManagement

import driver.models.Events
import driver.network.EventNetRepository
import javax.inject.Inject

class EventManagerImpl  @Inject constructor(
    private val eventNetRepository: EventNetRepository
): EventManager{
    override suspend fun getAllEvents(): List<Events>? {
        return eventNetRepository.getAllEvents()
    }


}