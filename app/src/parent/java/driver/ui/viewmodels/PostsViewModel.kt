package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.postUploadManagement.PostUploadManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val postsUploadManager: PostUploadManager) :ViewModel(){
    private val _uploadedPosts: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val postDetails: StateFlow<List<String>> = _uploadedPosts.asStateFlow()

    fun uploadPosts(image: ByteArray?){
        viewModelScope.launch {
            try {
                if (image != null) {
                    val postResponse = postsUploadManager.uploadPosts(image)
                    _uploadedPosts.update{currentResponse ->
                        currentResponse+postResponse
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun deletePosts(){
        _uploadedPosts.value = emptyList()
    }
}