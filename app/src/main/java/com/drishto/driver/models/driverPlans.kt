package com.drishto.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DriverPlans (
      var id : Int,
      var planCode :  String ,
      var companyId : Int,
      var companyName :  String ,
      var companyCode :  String ,
      var operatorId : Int,
      var operatorName :  String ,
      var operatorCode :  String ,
      var routeName :  String ,
      var contractId : Int?,
      var contractCode : String? ,
      var startTime :  String ,
      var from :  String ,
      var till :  String ,
      var status : String ,
      var userName :  String ,
      var userId : Int,
      var userEmail :  String ,
      var vehicleNumber :  String ,
      var driverId : Int
)