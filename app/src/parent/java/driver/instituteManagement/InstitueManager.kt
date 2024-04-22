package driver.instituteManagement

import driver.models.ContactList


interface InstitueManager {
    fun addInstitute(
        instituteName: String,
        contactList: List<ContactList>,
        description: String,
        facilityFields: List<String>,
        address: String,
        state: String,
        city: String,
        latitude: String,
        longitude:String
    );
}