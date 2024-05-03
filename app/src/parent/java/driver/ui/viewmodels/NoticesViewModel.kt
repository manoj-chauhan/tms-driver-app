package driver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.NoticeManagement.NoticeManager
import driver.ui.pages.Notice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class NoticesViewModel @Inject constructor(
    private val addNotice: NoticeManager,

) : ViewModel() {

    private val _notices: MutableStateFlow<List<Notice>?> = MutableStateFlow(null)
    val notices: StateFlow<List<Notice>?> = _notices.asStateFlow()


    fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                val fetchedNotices = AddNoti.getAllNotices()
//                _notices.value = fetchedNotices
            } catch (e: Exception) {

                _notices.value = listOf()
            }
        }
    }

    fun addNotice (
        noticeName: String,
        selectedDate: String,
        description: String,
        fileByteArray: ByteArray,



        ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                addNotice.addNotice(noticeName,description, selectedDate)
                Log.d("value","$noticeName")
                Log.d("lat", "$selectedDate")
                Log.d("long", "$description")



            } catch (e: Exception) {

            }
        }


    }
}