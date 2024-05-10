package driver.postUploadManagement

import driver.models.CommentPost
import driver.models.PostUpload
import driver.models.PostsFeed

interface PostUploadManager {
    fun uploadPosts(image: ByteArray, mimeType: String): String
    fun addPost(media: List<PostUpload?>, message: String, profileId: String)
    fun getFeedPosts(profile:String):List<PostsFeed>?

    fun getPostDetail(postId:String):PostsFeed?
    fun getPostComments(postId: String):List<CommentPost>?

}