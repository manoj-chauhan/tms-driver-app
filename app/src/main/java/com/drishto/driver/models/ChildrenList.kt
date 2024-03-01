package com.drishto.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChildrenList (
     var id : Int,
     var name :  String ,
     var guardianName :  String  ,
     var dateOfBirth :  String ,
     var gender : String ,
     var schoolName : String  ,
     var schoolAddress : String ,
     var standard :  String ,
     var primaryPhoneNumber :  String ,
     var secondaryPhoneNumber : String?,
     var boardingPlaceId : Int,
     var boardingPlaceName :  String ,
     var deBoardingPlaceId : Int,
     var deBoardingPlaceName : String?,
     var addedBy :  String ,
     var addedAt :  String
)