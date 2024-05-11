package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParentTrip(
    var tripCode: String,
    var tripName: String,
    var passengerTripId:Int,
    var driverName: String?,
    var vehicleNumber: String?,
    var status: String,
    var companyId: Int = 1 ,
    var tripDate: String,
    var routeName: String?,
    var childName: String,
    val childSchool:String,
    val childStandard: String,
    val currentLocation: String?,
    val lastUpdateTime: String,
    val deBoardingPlaceName:String,
    val deBoardingPlaceTime: String,
    val boardingPlaceName: String,
    val boardingPlaceTime: String,
    val boardingPlaceId:String ,
    val deBoardingPlaceId:String,
    val tripTime:String,
    val routeId:Int?,
    val delay: Int,
    val estDistance: Int,
    val estTime: Int
)

@JsonClass(generateAdapter = true)
data class PresentTrip(
    val day: String,
    val arrival: String,
    val mesage:String,
    val reaching: String,
    val destination: String,
    val destinationPlace:String,
    val timing:String
)

fun getDummyPresentTrip(): List<PresentTrip> {
    return listOf(
        PresentTrip(
            day= "Today 09:00 AM",
            arrival = "Arriving in 20 minutes",
            mesage="Please reach to your stop ",
            reaching = "D block, Swaroop Nagar, Delhi",
            destination = "Expected to reach destination",
            destinationPlace =" Maharaja Agrasen Adarsh Public School, Pitampura",
            timing = "10:00AM"
        ),
        PresentTrip(
            day= "Today 09:00 AM",
            arrival = "Arriving in 20 minutes",
            mesage="Please reach to your stop ",
            reaching = "D block, Swaroop Nagar, Delhi",
            destination = "Expected to reach destination",
            destinationPlace =" Maharaja Agrasen Adarsh Public School, Pitampura",
            timing = "10:00AM"
        ),
        PresentTrip(
            day= "Today 09:00 AM",
            arrival = "Arriving in 20 minutes",
            mesage="Please reach to your stop ",
            reaching = "D block, Swaroop Nagar, Delhi",
            destination = "Expected to reach destination",
            destinationPlace =" Maharaja Agrasen Adarsh Public School, Pitampura",
            timing = "10:00AM"
        ),
    )
}