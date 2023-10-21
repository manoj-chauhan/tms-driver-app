package com.samrish.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class History(
     val tripCode :  String ,
     val tripName :  String ,
     val assignedAt :  String ,
     val assignedTill :  String ,
     val operatorCompanyCode :  String ,
     val operatorCompanyName :  String ,
     val createdBy : Int,
     val tripDate:String,
     val customerCompanyName: String
)



data class AssignmentHistory(
     var history: MutableList<History>
)