package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountProfile (
     val id :  String,
     val name :  String,
     val displayPicture : String?,
     val type :  String,
     val anchor :  String,
     val gender:String?,
     val address : Address?,
     val createdAt : String?,
     val acl : List<String>?,
     val sentRequests: Int?,
     val pendingRequests:Int?,
     val connections:Int?
)
