package driver.postUploadManagement

interface PostUploadManager {
    suspend  fun uploadPosts(image: ByteArray): String
}