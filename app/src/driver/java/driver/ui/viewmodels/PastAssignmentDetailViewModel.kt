package driver.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.Documents
import com.drishto.driver.models.PastAssignmentDetailData
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
class PastAssignmentDetailViewModel @Inject constructor(private  val errorManager: ErrManager, application: Application, private val tripManager: TripManager): AndroidViewModel(application)  {
    private val _pastassignmentDetail: MutableStateFlow<PastAssignmentDetailData?> = MutableStateFlow(null)
    val pastassignmentDetail: StateFlow<PastAssignmentDetailData?> = _pastassignmentDetail.asStateFlow()
    var status :String ?= null

    fun fetchAssignmentDetail(context: Context, tripId:Int, tripCode:String, operatorId:Int){

        Log.i("","$tripId")

        val channel1 = Channel<TripDetail>()
        var document: MutableList<Documents>? = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            document = tripManager.getDocuments(tripId, operatorId)
        }


        viewModelScope.launch(Dispatchers.IO) {
            val tripDetail = tripManager.getTripDetail(tripCode, operatorId)
            channel1.send(tripDetail)
        }

            Log.d("Ended", "here is assignment details ")
            viewModelScope.launch(Dispatchers.IO) {
                val tripDetail = channel1.receive();

                Log.d("TAG", "fetchAssignmentDetail:${pastassignmentDetail.value?.tripDetail?.status} ")
                _pastassignmentDetail.update { _ ->
                    PastAssignmentDetailData(
                        tripDetail,
                        document,
                        true
                    )
                }
                Log.i("Fuel", "Response1: $tripDetail, $document")
            }
    }
}