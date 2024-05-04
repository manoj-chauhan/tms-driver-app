package driver.postUploadManagement

import driver.models.PostUpload
import driver.models.PostsFeed

interface PostUploadManager {
    fun uploadPosts(image: ByteArray, mimeType: String): String
    fun addPost(media: List<PostUpload?>, message: String)
    fun getFeedPosts(profile:String):List<PostsFeed>?

}