package driver.instituteManagement



import driver.models.ContactList
import driver.network.InstituteNetRepository
import javax.inject.Inject

class InstituteImpl @Inject constructor(
    private val AddInstituteNetRepository: InstituteNetRepository,
): InstitueManager {
    override  fun addInstitute(
        instituteName: String,
        contactList: List<ContactList>,
        description: String,
        facilityFields: List<String>,
        address: String,
        state: String,
        city: String
    ) {
        return AddInstituteNetRepository.addInstitute(instituteName,contactList,description,facilityFields,address,state,city)


    }

}
