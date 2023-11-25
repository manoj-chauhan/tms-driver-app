package com.samrish.driver.network

import android.content.Context
import android.os.Build
import android.util.Log
import com.samrish.driver.database.TelemetryRepository
import com.samrish.driver.models.Telemetry
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@JsonClass(generateAdapter = true)
data class TelemetryPost(
    var deviceIdentifier: String,
    var deviceName: String,
    var latitude:Double,
    var longitude: Double,
    var time: LocalDateTime
)

class DateTimeAdapter : JsonAdapter<LocalDateTime>() {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateTimeFormatter) {
                dateTimeFormatter.parse(dateAsString) as LocalDateTime
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value != null) {
            synchronized(dateTimeFormatter) {
                writer.value(dateTimeFormatter.format(value))
            }
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd HH:mm:ss") // define your server format here
    }
}

class TelemetryNetRepository @Inject constructor(@ApplicationContext private val context: Context,  private val telemetryRepository: TelemetryRepository)  {

    suspend fun sentTelemetry(telemetry: Telemetry) {

        val moshi = Moshi.Builder()
            .add(DateTimeAdapter())
            .build()
        val jsonAdapter: JsonAdapter<TelemetryPost> = moshi.adapter(TelemetryPost::class.java)
        val deviceName = Build.MANUFACTURER + " " + Build.MODEL
        val requestBody: String = jsonAdapter.toJson(TelemetryPost(
            deviceIdentifier = telemetry.deviceIdentifier,
            deviceName = deviceName,
            latitude = telemetry.latitude,
            longitude = telemetry.longitude,
            time = telemetry.time)
        )

        Log.d("Telemetry", requestBody)

//        getAccessToken(context)?.let { accessToken ->
//
//            val url = context.resources.getString(R.string.url_device_matrix)
//            val fuelManager = FuelManager()
//            val (_, response, result) = fuelManager.post(url).authentication().bearer(accessToken)
//                .jsonBody(requestBody)
//                .response()
//
//            result.fold(
//                { _ ->
//                   val id =  telemetryRepository.getTelemetryId(telemetry.latitude, telemetry.longitude, telemetry.time)
//                    telemetryRepository.updateTelemetryStatus(id, true)
//
//                    Log.d("TAG", "sentTelemetry: $id")
//                    Log.d("Telemetry", "Telemetry Posted")
//                },
//                { error ->
//                    Log.d("Telemetry", "Telemetry Failed $error")
//                })
//        }

    }
}