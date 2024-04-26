package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.network.LikeNetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val likeNetRepository: LikeNetRepository
) : ViewModel() {

    fun likePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            likeNetRepository.likePost(postId)
        }
    }
}
