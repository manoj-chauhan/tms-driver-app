package driver.postUploadManagement

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.CommentPost
import driver.models.PostUpload
import driver.models.PostsFeed
import driver.network.PostNetRepository
import javax.inject.Inject

class PostUploadManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context, private val postNetRepository: PostNetRepository) :PostUploadManager {
    override fun uploadPosts(image: ByteArray, mimeType:String): String{
        return postNetRepository.getMediaID(image,mimeType)
    }

    override fun addPost(media: List<PostUpload?>, message: String, profileId: String) {
        return postNetRepository.uploadPosts(media, message, profileId)
    }

    override fun getFeedPosts(profileId:String): List<PostsFeed>? {
        return postNetRepository.getAllFeeds(context, profileId)
    }

    override fun getPostDetail(postId: String): PostsFeed? {
        return postNetRepository.getPostDetail(context,postId)
    }

    override fun getPostComments(postId: String): List<CommentPost>? {
        return postNetRepository.getCommentsByPost(context, postId)
    }


}