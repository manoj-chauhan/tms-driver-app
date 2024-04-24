package driver.ui.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.errormgmt.ErrManager
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.instituteManagement.InstitueManager
import driver.models.AddInstituteSavedData
import driver.models.ContactList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddInstitueViewModel @Inject constructor(
    private val addInstitute:InstitueManager,
    private val errorManager: ErrManager,
) : ViewModel() {


    private var storedInstituteName: String = ""
    private var storedDescription: String = ""
    private var storedAddress: String = ""
    private var storedCity: String = ""
    private var storedState: String = ""
    private var storedContactEntries: List<ContactList> = listOf()

    fun storeData(
        instituteName: String,
        description: String,
        address: String,
        city: String,
        state: String,
        contactEntries: List<ContactList>
    ) {
        storedInstituteName = instituteName
        storedDescription = description
        storedAddress = address
        storedCity = city
        storedState = state
        storedContactEntries = contactEntries

        Log.d("TAG", "storeData: $storedInstituteName, $storedCity, $storedAddress, $storedDescription ")

    }

    fun getStoredData(): AddInstituteSavedData {
        return AddInstituteSavedData(
            instituteName = storedInstituteName,
            description = storedDescription,
            address = storedAddress,
            city = storedCity,
            state = storedState,
            contactEntries = storedContactEntries
        )
    }

    fun addInstitute (
        instituteName: String,
        contactList: List<ContactList>,
        description: String,
        facilityFields: List<String>,
        address: String,
        state: String,
        city: String,
//        geoCordinates:List<GeoCordinates>
        latitude:String,
        longitude:String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                addInstitute.addInstitute(instituteName,contactList,description,facilityFields,address,state,city,latitude,longitude)
                Log.d("value","$contactList")
                Log.d("lat", "$latitude")
                Log.d("long", "$longitude")



            } catch (e: Exception) {

            }
        }


    }


}


