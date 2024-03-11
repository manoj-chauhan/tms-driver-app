package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.tripManagement.ParentTripManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FeedbackVIewModel @Inject constructor(private val parentTripManager: ParentTripManager) : ViewModel()  {
    fun sendFeedback(userId:Int, message: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                parentTripManager.sendFeedback(userId, message)
            }
            catch(e:Exception){

            }
        }
    }
}