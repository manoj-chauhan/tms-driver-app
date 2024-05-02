package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import driver.network.NoticeNetRepository
import driver.ui.pages.Notice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoticesViewModel(
    private val noticeNetRepository: NoticeNetRepository
) : ViewModel() {

    private val _notices: MutableStateFlow<List<Notice>?> = MutableStateFlow(null)
    val notices: StateFlow<List<Notice>?> = _notices.asStateFlow()


    fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedNotices = noticeNetRepository.getAllNotices()
                _notices.value = fetchedNotices
            } catch (e: Exception) {

                _notices.value = listOf()
            }
        }
    }
}