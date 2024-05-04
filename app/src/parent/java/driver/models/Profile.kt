package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountProfile (
     val id :  String ,
     val name :  String ,
     val displayPicture : String?,
     val type :  String ,
     val anchor :  String ,
     val address : String?,
     val createdAt : String?,
     val acl : List<String>?
)
