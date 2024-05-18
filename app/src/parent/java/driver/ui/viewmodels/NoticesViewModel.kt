package driver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.NoticeManagement.NoticeManager
import driver.models.Event
import driver.models.Notice_List
import driver.postUploadManagement.PostUploadManager
import driver.ui.pages.NoticeListPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class NoticesViewModel @Inject constructor(
    private val addNotice: NoticeManager,
    private val postUploadManager: PostUploadManager,
    private val noticeManager: NoticeManager

) : ViewModel() {

    private val _noticelist: MutableStateFlow<List<Notice_List>?> = MutableStateFlow(null)
    val noticelist: StateFlow<List<Notice_List>?> = _noticelist.asStateFlow()

    private val _noticeDetail: MutableStateFlow<Notice_List?> = MutableStateFlow(null)
    val noticeDetail: StateFlow<Notice_List?> = _noticeDetail.asStateFlow()


    fun getAllNotices() {
        CoroutineScope(Dispatchers.IO).launch {
            val notices = noticeManager.getAllNotices()
            _noticelist.update {
                notices
            }
        }
    }

    fun getNoticeById(noticeId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val notice = noticeManager.getNoticeById(noticeId)
                _noticeDetail.value = notice
            } catch (e: Exception) {

                Log.d("notice by id error", "getNoticeById: error in getting noticeByID")
            }
        }
    }




    private val _uploadedPosts: MutableStateFlow<Pair<String, String>?> = MutableStateFlow(null)
    val postDetails: StateFlow<Pair<String, String>?> = _uploadedPosts.asStateFlow()
    fun uploadPosts(image: ByteArray?, mimeType: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                if (image != null && mimeType != null) {
                    val postResponse = postUploadManager.uploadPosts(image, mimeType)

                    _uploadedPosts.update {
                        postResponse to mimeType
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun addNotice(
        noticeName: String,
        selectedDate: String,
        description: String,
        ) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                noticeManager.addNotice(noticeName, selectedDate, description)
            } catch (e: Exception) {
                null

            }
        }


    }
}