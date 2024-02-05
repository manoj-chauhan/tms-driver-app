package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class ParentPastTrip (
     val childName :  String ,
     val childSchool :  String ,
     val childStandard :  String ,
     val tripId : Int,
     val tripCode :  String ,
     val tripName :  String,
     val driverName : String?,
     val vehicleNumber : String?,
     val status :  String ,
     val currentLocation :  String? ,
     val lastUpdateTime :  String ,
     val tripDate :  String ,
     val tripTime :  String,
    val  routeId : Int,
     val routeName : String
)