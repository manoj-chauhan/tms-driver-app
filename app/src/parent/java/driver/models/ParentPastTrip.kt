package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParentPastTrip (
     val childName :  String ,
     val childSchool :  String ,
     val childStandard :  String ,
     val passengerTripId: Int,
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
     val routeName : String,
     val boardingPlaceName: String,
     val deBoardingPlaceName: String,
     val deBoardingPlaceTime: String?,
)

@JsonClass(generateAdapter = true)
data class PastTrip (
    val date:String,
    val status: String,
    val arrival:String,
    val arrivalTime:String,
    val departure:String,
    val departureTime:String
)

fun getDummyPastTrip(): List<PastTrip> {
    return listOf(
        PastTrip(
            date= "Yesterday",
            status = "Arrived Late",
            arrival = "Maharaja Agrasen Public School ",
            arrivalTime = "01:00 pm",
            departure = "D block, Swaroop Nagar",
            departureTime = "01:30pm"
        ),
        PastTrip(
            date= "22-Jun-2024",
            status = "Arrived Late",
            arrival = "Mother Divine Public School ",
            arrivalTime = "01:00 pm",
            departure = "D block, Swaroop Nagar",
            departureTime = "01:30pm"
        ),
        PastTrip(
            date= "25-Jul-2024",
            status = "Arrived Late",
            arrival = "Mount Abu International Public School ",
            arrivalTime = "10:00 pm",
            departure = "Mother Divine Public School",
            departureTime = "01:30pm"
        ),
    )
}