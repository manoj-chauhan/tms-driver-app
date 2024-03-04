package driver.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.DriverPlan.DriverPlanManager
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.ChildrenList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DriverPlanDetailsViewModel @Inject constructor(private  val errorManager: ErrManager, application: Application, private val driverPlan: DriverPlanManager): AndroidViewModel(application)  {
    private val  _childrenList: MutableStateFlow<List<ChildrenList>?> = MutableStateFlow(null)
    val childrenList: StateFlow<List<ChildrenList>?> = _childrenList.asStateFlow()

    fun fetchParentTrip(context: Context, operatorId:Int, planId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val tripList = driverPlan.getChildrenList(operatorId, planId)
            _childrenList.update { _ ->
                tripList
            }
                Log.d("TAG", "fetchParentTrip: $tripList")
        }

    }



}