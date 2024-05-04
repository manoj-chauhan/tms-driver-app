package driver.EventManagement

import driver.models.EventRegistration
import driver.models.Events

interface EventManager {
    suspend fun getAllEvents():List<Events>?
    suspend fun addEvent(event: EventRegistration)
}