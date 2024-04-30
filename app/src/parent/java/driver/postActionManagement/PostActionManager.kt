package driver.postActionManagement

import driver.models.Comments

interface PostActionManager {
    suspend fun uploadPostComment(postId:String, message: String)
    suspend fun getPostComment(postId: String):List<Comments>?
}