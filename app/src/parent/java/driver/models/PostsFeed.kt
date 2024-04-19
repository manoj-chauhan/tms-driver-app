package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsFeed(
     val id :  String ,
     val createdBy : String?,
     val likes : Int?,
     val comments : Int?,
     val scope :  String?,
     val shares : String?,
     val message : String?,
     val media : List<MediaList>
)
