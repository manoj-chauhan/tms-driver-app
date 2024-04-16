package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.postUploadManagement.PostUploadManager
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val postsUploadManager: PostUploadManager) :ViewModel(){

    fun uploadPosts(image: File?){
        viewModelScope.launch {
            try {
                if (image != null) {
                    postsUploadManager.uploadPosts(image)
                }
            } catch (e: Exception) {
            }
        }
    }
}