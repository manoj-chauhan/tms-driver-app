package driver.postUploadManagement

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.PostUpload
import driver.network.PostNetRepository
import javax.inject.Inject

class PostUploadManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context, private val postNetRepository: PostNetRepository) :PostUploadManager {
    override fun uploadPosts(image: ByteArray): String{
        return postNetRepository.getMediaID(image)
    }

    override fun addPost(media: List<PostUpload>, message: String) {
        return postNetRepository.uploadPosts(media, message)
    }
}