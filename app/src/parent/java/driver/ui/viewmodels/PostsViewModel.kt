package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.models.PostUpload
import driver.models.PostsFeed
import driver.postUploadManagement.PostUploadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val postsUploadManager: PostUploadManager) :
    ViewModel() {
    private val _uploadedPosts: MutableStateFlow<List<Pair<String, String>>?> = MutableStateFlow(emptyList())
    val postDetails: StateFlow<List<Pair<String, String>>?> = _uploadedPosts.asStateFlow()

    private val _postsFeed: MutableStateFlow<List<PostsFeed>?> = MutableStateFlow(emptyList())
    val postFeedsDetails: StateFlow<List<PostsFeed>?> = _postsFeed.asStateFlow()

    fun uploadPosts(image: ByteArray?, mimeType: String?){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (image != null  && mimeType != null) {
                    val postResponse = postsUploadManager.uploadPosts(image, mimeType)
                    _uploadedPosts.update { currentResponse ->
                        currentResponse?.plus(postResponse to mimeType)
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun deletePosts() {
        _uploadedPosts.update { emptyList() }
    }

    fun addPost(media: List<PostUpload?>, message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                postsUploadManager.addPost(media, message)
            } catch (e: Exception) {
            }
        }
    }

    fun getPosts(profileId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val postsList = postsUploadManager.getFeedPosts(profileId)
            _postsFeed.update {
                postsList
            }
        }
    }
}