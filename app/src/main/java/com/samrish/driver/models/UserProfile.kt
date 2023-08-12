package com.samrish.driver.models;



class UserProfile(
    val id: Int,
    val name: String,
    val authProvider: String,
    val userName: String

) {
    override fun toString(): String {
        return "UserProfile(id=$id, name='$name', authProvider='$authProvider', userName='$userName')"
    }
}
