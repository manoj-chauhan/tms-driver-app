package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.postActionManagement.PostActionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostActionsViewModel @Inject constructor(private val postActionManager: PostActionManager) : ViewModel() {
    fun uploadComments(postId: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postActionManager.uploadPostComment(postId, message)
            }catch (e:Exception){

            }
        }

    }
}