package driver.postActionManagement

interface PostActionManager {
    suspend fun uploadPostComment(postId:String, message: String)
}