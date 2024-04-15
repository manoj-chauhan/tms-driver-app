package driver.educationManagement

import driver.models.EducationList
import driver.models.ParentTrip
import driver.network.EducationNetRepository
import driver.network.ParentTripNetRepository
import javax.inject.Inject

class EducationImpl @Inject constructor(
    private val EducationNetRepository: EducationNetRepository,
): EducationManager   {
    override fun getEducationList(): List<EducationList>? {
        return EducationNetRepository.fetchEducationList()

    }

}

// EducationManagerImpl.kt


