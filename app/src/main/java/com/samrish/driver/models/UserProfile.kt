package com.samrish.driver.models;



class UserProfile(
    val id: Int,
    val name: String,
    val authProvider: String,
    val userName: String,
    val companies: List<Company>

) {
    override fun toString(): String {
        return "UserProfile(id=$id, name='$name', authProvider='$authProvider', userName='$userName', companies=$companies)"
    }
}


class Company(
    val id: Int,
    val code: String,
    val name: String,
    val roles: List<String>


) {
    override fun toString(): String {
        return "Company(id=$id, code='$code', name='$name', roles=$roles)"
    }
}