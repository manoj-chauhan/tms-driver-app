package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.models.Comments
import driver.postActionManagement.PostActionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostActionsViewModel @Inject constructor(private val postActionManager: PostActionManager) : ViewModel() {

    private val _comments: MutableStateFlow<List<Comments>?> = MutableStateFlow(emptyList())
    val commentsList: StateFlow<List<Comments>?> = _comments.asStateFlow()
    fun uploadComments(postId: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postActionManager.uploadPostComment(postId, message)
            }catch (e:Exception){

            }
        }

    }

    fun getComments(postId: String){
        try {
            viewModelScope.launch {
                val commentList = postActionManager.getPostComment(postId)
                _comments.update {
                    commentList
                }
            }

        }catch (e:Exception){

        }
    }
}