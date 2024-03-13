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

@JsonClass(generateAdapter = true)
data class scheduleList (
      var id : Int,
      var planCode :  String ,
      var companyId : Int,
      var companyName :  String,
      var companyCode :  String ,
      var operatorId : Int,
      var operatorName :  String ,
      var operatorCode :  String ,
      var contractId : Int,
      var contractCode :  String ,
      var startTime :  String ,
      var from :  String ,
      var till : String ,
      var days: Map<String, Boolean>,
      var status :  String ,
      var createdAt :  String,
      var createdById : Int,
     var  createdByName : String ,
     var  lastUpdatedByAt :  String ,
     var  updaterById : Int,
     var  updaterByName :  String ,
     var  routeName :  String ,
     val tripPlanScheduleList: List<TripSchedulesList>
)

@JsonClass(generateAdapter = true)
data class TripSchedulesList(
       var placeId  : Int,
       var placeCode  :   String  ,
       var placeName  :   String  ,
       var stopOrder  : Int?,
       var travelTime  : Int,
       var haltTime  : Int,
       var travelDistance  : Double,
       var creatorId  : Int,
       var creatorName  :   String  ,
       var createdAt  :   String  ,
       var updaterName  :  String  ,
       var updaterId  : Int,
       var updatedAt  :   String,
    var departure:String?,
    var arrival:String?
)


@JsonClass(generateAdapter = true)
data class childrenAddPlan(
    var name: String,
    var schoolName: String,
    var primaryPhoneNumber: String,
    var standard: String,
    var gender: String,
    var secondaryPhoneNumber: String,
    var dateOfBirth: String,
    var guardianName: String,
    var schoolAddress: String,
    var tripPlanId: Int,
    var boardingPlaceId: Int,
    var deBoardingPlaceId: Int
)