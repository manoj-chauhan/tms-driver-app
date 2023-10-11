package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.samrish.driver.database.AppDatabase
import com.samrish.driver.database.User
import com.samrish.driver.services.getTripDetail
import com.samrish.driver.services.vehicleDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class VehicleAssignment(
    val vehicleId : Int,
    val vehicleNumber : String,
    val companyId : Int,
    val companyName : String,
    val assignerName : String,
    val assignedAt : String,
    val vehicleSize : Int,
    val model : String,
    val brand : String,
    val fuelType : String
    
    
)

class VehicleAssignmentViewModel : ViewModel() {

    // Expose screen UI state
    private val _currentAssignment:MutableStateFlow<VehicleAssignment?> = MutableStateFlow(null)
    val currentAssignment: StateFlow<VehicleAssignment?> = _currentAssignment.asStateFlow()

    // Handle business logic
    fun fetchAssignmentDetail(context:Context) {

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val userDao = db.userDao()
            userDao.insertUsers(User(1, "Manoj", "Chauhan"))
            userDao.insertUsers(User(2, "Atul", "Chauhan"))
            userDao.insertUsers(User(3, "Ankit", "Chauhan"))
            userDao.insertUsers(User(4, "Krish", "Chauhan"))
            userDao.insertUsers(User(5, "Vaibhav", "Chauhan"))
            userDao.insertUsers(User(6, "Dev", "Chauhan"))
            val userList = userDao.loadUsers()
            userList.forEach { user -> Log.i("MyDb","Name: "+ user.firstName + " " + user.lastName) }

        }

        vehicleDetails(context, onVehicleDetailFetched = {
            _currentAssignment.update { assignment ->
                VehicleAssignment(it.vehicleId, it.vehicleNumber, it.companyId, it.companyName, it.assignerName,it.assignedAt, it.vehicleSize, it.model, it.brand, it.fuelType )
            }
//        isApiCalled.value = true
        })
    }
}