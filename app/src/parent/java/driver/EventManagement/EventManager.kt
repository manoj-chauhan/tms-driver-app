package driver.EventManagement

import driver.models.Event
import driver.models.EventRegistration

interface EventManager {
    suspend fun getAllEvents():List<Event>?
    suspend fun addEvent(event: EventRegistration, profileId: String)

    suspend fun getEventById(eventId:String):Event?
}