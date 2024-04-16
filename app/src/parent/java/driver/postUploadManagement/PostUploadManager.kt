package driver.postUploadManagement

import java.io.File

interface PostUploadManager {
    fun uploadPosts(image: File)
}