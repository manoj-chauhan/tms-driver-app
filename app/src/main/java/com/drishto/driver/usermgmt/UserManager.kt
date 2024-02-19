package com.drishto.driver.usermgmt

import android.graphics.Bitmap
import com.drishto.driver.models.Student

interface UserManager {

fun editUserName(name:String)

fun childrenList():List<Student>?

fun uploadPhoto(image: ByteArray, userId: Int)

fun getUploadedImage(userId:Int):Bitmap?
}