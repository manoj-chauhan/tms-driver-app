package driver.postUploadManagement

import driver.models.PostUpload

interface PostUploadManager {
    fun uploadPosts(image: ByteArray): String

    fun addPost(media: List<PostUpload>, message: String)
}