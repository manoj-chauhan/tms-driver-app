package driver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.models.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.educationManagement.EducationManager
import driver.models.EducationList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class EducationListViewModel @Inject constructor(private val EducationList: EducationManager) :
    ViewModel() {
    private val _educationList: MutableStateFlow<List<EducationList>?> = MutableStateFlow(null)
    val educationList: StateFlow<List<EducationList>?> = _educationList.asStateFlow()

    fun getEducationList() {
        viewModelScope.launch(Dispatchers.IO) {
            val educationList = EducationList.getEducationList()
            Log.d("checking1", "$educationList")
            _educationList.update { _ ->
                educationList
            }

        }


    }

}