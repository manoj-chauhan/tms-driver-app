package driver.ui.viewmodels

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.ActiveStatusDetail
import com.drishto.driver.models.AssignmentDetailData
import com.drishto.driver.models.Documents
import com.drishto.driver.models.Schedule
import com.drishto.driver.models.TripDetail
import com.drishto.driver.tripmgmt.TripManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentDetailViewModel @Inject constructor(private  val errorManager: ErrManager, application: Application, private val tripManager: TripManager): AndroidViewModel(application)  {

    private val _assignmentDetail: MutableStateFlow<AssignmentDetailData?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<AssignmentDetailData?> = _assignmentDetail.asStateFlow()

    fun fetchAssignmentDetail(context: Context, tripId:Int, tripCode:String, operatorId:Int) {

        Log.i("","$tripId")

        val channel1 = Channel<TripDetail>()
        val channel2 = Channel<ActiveStatusDetail>()
        val channel3 = Channel<Schedule>()

        var document: MutableList<Documents>? = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            document = tripManager.getDocuments(tripId, operatorId)
        }


        viewModelScope.launch(Dispatchers.IO) {
            val tripDetail = tripManager.getTripDetail(tripCode, operatorId)
            channel1.send(tripDetail)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val activeStatus = tripManager.getTripStatus(tripId, operatorId)
            channel2.send(activeStatus)
        }


        viewModelScope.launch(Dispatchers.IO) {
            val schedule = tripManager.getTripSchedule(tripCode, operatorId)
            channel3.send(schedule)
        }



        viewModelScope.launch(Dispatchers.IO) {

            val tripDetail = channel1.receive()
            val statusDetail = channel2.receive();
            val schedule = channel3.receive()


            _assignmentDetail.update { _ ->
                AssignmentDetailData(
                     tripDetail,
                     statusDetail,
                     schedule,
                     document,
                    true
                )
            }
            Log.i("Fuel", "Response1: $tripDetail")
            Log.i("Fuel", "Response2: $statusDetail")
            Log.i("Fuel", "Response3: $schedule")
        }
    }
    fun startTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deviceIdentifier = Settings.Secure.getString(
                    getApplication<Application>().contentResolver, Settings.Secure.ANDROID_ID
                )

                tripManager.startTrip(tripCode, operatorId, deviceIdentifier)
                fetchAssignmentDetail(context,tripId, tripCode, operatorId)

            } catch (e: Exception) {
            }
        }
    }

    fun checkInTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int, placeCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                tripManager.checkInTrip(placeCode, tripCode, operatorId)
                fetchAssignmentDetail(context, tripId, tripCode, operatorId)
            }catch (e:Exception){
            }
        }
    }

    fun departTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                tripManager.departTrip(tripCode,operatorId)
                fetchAssignmentDetail(context,tripId, tripCode, operatorId)
            }catch (e:Exception){

            }
        }
    }

    fun cancelTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int,  navController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                tripManager.cancelTrip(tripCode,operatorId, navController)

            } catch (e: Exception) {

            }
        }
    }

    fun endTrip(context: Context,tripId:Int, tripCode: String, operatorId: Int,  navController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
              tripManager.endTrip(tripCode,operatorId,navController)
            } catch(e:Exception){

            }
        }
    }

    fun documents(context: Context, tripId: Int){

    }
}