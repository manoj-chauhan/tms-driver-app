package driver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.errormgmt.ErrManager


import dagger.hilt.android.lifecycle.HiltViewModel
import driver.instituteManagement.InstitueManager
import driver.models.ContactList
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddInstitueViewModel @Inject constructor(
    private val addInstitute:InstitueManager,
    private val errorManager: ErrManager,
) : ViewModel() {


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


