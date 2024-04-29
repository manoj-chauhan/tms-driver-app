package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comments (
     val id :  String ,
     val content :  String ,
     val commentedBy :  String ,
     val onModel :  String ,
     val modelId :  String ,
     val likes : Int,
     val comments : Int
)