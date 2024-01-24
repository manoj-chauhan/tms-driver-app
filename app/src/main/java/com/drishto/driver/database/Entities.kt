package com.drishto.driver.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = " first_name" ) val firstName: String?,
    @ColumnInfo(name =  "last_name" ) val lastName: String?
)

@Entity
data class Telemetry(
    @ColumnInfo(name =  "latitude" ) val latitude: Double,
    @ColumnInfo(name =  "longitude" ) val longitude: Double,
    @ColumnInfo(name =  "DateTime" ) val time:LocalDateTime,
    @ColumnInfo(name =  "DataLoaded" ) val isDataLoaded: Boolean,
    @ColumnInfo(name = "deviceIdentifier") val deviceIdentifier:String
){
    @PrimaryKey (autoGenerate = true)
    var id: Long= 0
}

@Entity
data class Trip(
    @ColumnInfo(name  ="tripCode") val tripCode :  String ,
    @ColumnInfo(name  ="tripName") val tripName :  String,
    @ColumnInfo(name  ="status") val  status :  String ,
    @ColumnInfo(name  ="label") val label :  String ,
    @ColumnInfo(name  ="companyName") val companyName :  String ,
    @ColumnInfo(name  ="companyCode") val companyCode :  String ,
    @ColumnInfo(name  ="operatorCompanyName") val operatorCompanyName :  String ,
    @ColumnInfo(name  ="operatorCompanyCode") val operatorCompanyCode :  String ,
    @ColumnInfo(name  ="operatorCompanyId") val operatorCompanyId : Int,
    @ColumnInfo(name  ="tripDate") val tripDate :  String,
    @PrimaryKey val tripId : Int,

)

@Entity
data class History(
    @ColumnInfo(name = " state" ) val state: String,
    @ColumnInfo(name =  "description" ) val description: String,
    @ColumnInfo(name =  "time" ) val time: String

)
