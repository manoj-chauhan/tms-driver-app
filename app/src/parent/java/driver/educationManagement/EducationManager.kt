package driver.educationManagement

import driver.models.EducationList
import driver.models.ParentTrip

interface EducationManager {
    fun getEducationList(): List<EducationList>?
}




