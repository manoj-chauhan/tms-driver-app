package driver.EventManagement

import driver.models.Events

interface EventManager {
    suspend fun getAllEvents():List<Events>?
}