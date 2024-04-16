package driver.postUploadManagement

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.network.PostNetRepository
import java.io.File
import javax.inject.Inject

class PostUploadManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context, private val postNetRepository: PostNetRepository) :PostUploadManager {
    override fun uploadPosts(image: File){
        return postNetRepository.uploadPost(image)
    }
}