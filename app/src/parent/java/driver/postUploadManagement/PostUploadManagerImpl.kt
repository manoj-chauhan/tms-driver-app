package driver.postUploadManagement

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.network.PostNetRepository
import javax.inject.Inject

class PostUploadManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context, private val postNetRepository: PostNetRepository) :PostUploadManager {
    override suspend fun uploadPosts(image: ByteArray): String{
        return postNetRepository.uploadPost(image)
    }
}