package com.drishto.driver.usermgmt

import com.drishto.driver.models.Student

interface UserManager {

fun editUserName(name:String)

fun childrenList():List<Student>?
}