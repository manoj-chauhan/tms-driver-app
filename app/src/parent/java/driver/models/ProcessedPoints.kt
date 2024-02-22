package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessedPoints(
     val deviceIdentifier :  String,
     val  latitude : Double,
     val longitude : Double,
     val time :  String
)

@JsonClass(generateAdapter = true)
data class currentDriverLocation(
     val deviceIdentifier :  String,
     val  latitude : Double,
     val longitude : Double,
     val time :  String
)