package com.drishto.driver.usermgmt

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.drishto.driver.models.Student
import com.drishto.driver.network.UserNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userNetRepository: UserNetRepository,
    ) : UserManager {
    override fun editUserName(name: String) {
        return userNetRepository.editUserName(name)
    }

    override fun childrenList(): List<Student>? {
        return userNetRepository.getChildrenList()
    }

    override fun uploadPhoto(image: ByteArray, userId: Int) {
        Log.d("TAG", "uploadPhoto:${image} ")
        return userNetRepository.uploadProfileImage(image, userId)
    }

    override fun getUploadedImage(userId:Int): Bitmap? {
        return userNetRepository.getUploadedImage(userId)
    }
}