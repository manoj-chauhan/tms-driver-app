package driver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.NoticeManagement.NoticeManager
import driver.postUploadManagement.PostUploadManager
import driver.ui.pages.Notice
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
    private val postUploadManager: PostUploadManager


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
    private val _uploadedPosts: MutableStateFlow<Pair<String, String>?> = MutableStateFlow(null)
    val postDetails: StateFlow<Pair<String, String>?> = _uploadedPosts.asStateFlow()
    fun uploadPosts(image: ByteArray?, mimeType: String?){
        CoroutineScope(Dispatchers.IO).launch {
            try {

                if (image != null  && mimeType != null) {
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

    fun addNotice (
        noticeName: String,
        selectedDate: String,
        description: String,

        ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (noticeName != null  && selectedDate != null && description != null) {
                    val postResponse = NoticeManager.addNotice(noticeName, selectedDate, description)

                    _uploadedPosts.update {
                        postResponse to mimeType
                    }
                }
                addNotice.addNotice(noticeName,description, selectedDate)





            } catch (e: Exception) {
                null

            }
        }


    }
}