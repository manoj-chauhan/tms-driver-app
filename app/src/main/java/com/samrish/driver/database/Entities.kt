package com.samrish.driver.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = " first_name" ) val firstName: String?,
    @ColumnInfo(name =  "last_name" ) val lastName: String?
)

@Entity
data class Matrix(
    @ColumnInfo(name =  "latitude" ) val latitude: Double,
    @ColumnInfo(name =  "longitude" ) val longitude: Double,
    @ColumnInfo(name =  "DateTime" ) val time:String
){
    @PrimaryKey (autoGenerate = true)
    var id: Int= 0
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