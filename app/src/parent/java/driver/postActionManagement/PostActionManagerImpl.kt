package driver.postActionManagement

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.Comments
import driver.network.PostActionNetRepository
import javax.inject.Inject

class PostActionManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postActionNetRepository: PostActionNetRepository
) : PostActionManager {
    override suspend fun uploadPostComment(postId: String, message:String) {
        return postActionNetRepository.sendComment(postId, message)
    }

    override suspend fun getPostComment(postId: String): List<Comments>? {
        return postActionNetRepository.getComments(postId)
    }


}