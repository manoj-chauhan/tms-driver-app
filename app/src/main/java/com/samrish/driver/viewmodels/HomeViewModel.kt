package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.services.getAccessToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@JsonClass(generateAdapter = true)
data class VehicleAssignment(
    val vehicleId: Int,
    val vehicleNumber: String,
    val companyId: Int,
    val companyName: String,
    val assignerName: String,
    val assignedAt: String,
    val vehicleSize: Int,
    val model: String,
    val brand: String,
    val fuelType: String
)

@JsonClass(generateAdapter = true)
data class TripsAssigned(
    var tripCode: String,
    var tripName:String,
    var status: String,
    var label: String,
    var companyName: String,
    var companyCode: String,
    var operatorCompanyName: String,
    var operatorCompanyCode: String,
    var operatorCompanyId: Int,
    var tripDate: String,
    var tripId: Int
)

data class CurrentAssignmentData (
    var userLocationVisible: Boolean,
    var trips: MutableList<TripsAssigned>,
    var vehicle: VehicleAssignment
)


class HomeViewModel : ViewModel() {

    private val _currentAssignment: MutableStateFlow<CurrentAssignmentData?> = MutableStateFlow(null)
    val currentAssignment: StateFlow<CurrentAssignmentData?> = _currentAssignment.asStateFlow()

    fun fetchAssignmentDetail(context: Context) {

        val channel1 = Channel<VehicleAssignment>()
        val channel2 = Channel<MutableList<TripsAssigned>>()

        viewModelScope.launch(Dispatchers.IO) {
            val vehicleAssignmentUrl = context.resources.getString(R.string.url_vehicle_assignment)
            getAccessToken(context)?.let {
                val (request1, response1, result1) = vehicleAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(VehicleAssignment::class.java))

                result1.fold(
                    { vehicleAssignment -> channel1.send(vehicleAssignment)
//                        _currentAssignment.update { _ ->
//                            ////The below code is not supposed to be here, it is placed here just for testing serialisation using Moshi library
//                            val moshi = Moshi.Builder().build()
//                            val adapter: JsonAdapter<VehicleAssignment> =
//                                moshi.adapter(VehicleAssignment::class.java)
//                            val json: String? = adapter.toJson(vehicleAssignment)
//                            Log.i("Fuel", "Generated: $json")
//                            ////The above code is not supposed to be here, it is placed here just for testing serialisation using Moshi library
//                            vehicleAssignment
//                        }
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val tripAssignmentUrl = context.resources.getString(R.string.url_trips_list)
            getAccessToken(context)?.let {

                val assignedTripType = Types.newParameterizedType(MutableList::class.java, TripsAssigned::class.java)
                val adapter: JsonAdapter<MutableList<TripsAssigned>> = Moshi.Builder().build().adapter(assignedTripType)


                val (request1, response1, result1) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))

                result1.fold(
                    {
                        tripAssignments -> channel2.send(tripAssignments)

                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val vehicleAssignment = channel1.receive();
            val tripsAssignment = channel2.receive();
            _currentAssignment.update { old ->
                CurrentAssignmentData(
                    old?.userLocationVisible ?: false,
                    tripsAssignment,
                    vehicleAssignment
                )
            }

            addNewTrip( )
            Log.i("Fuel", "Response1: $vehicleAssignment")
            Log.i("Fuel", "Response2: $tripsAssignment")

            Log.i("Fuel", "All Coroutines Finished!")
        }


    }

    private fun addNewTrip() {

        sendNotification("New Trip Assigned", "You have a new trip assignment.", )
    }

    private fun sendNotification(s: String, s1: String) {
        val targetToken =
                "eEwtmXJjSIu77DY9nZaHYn:APA91bFO5Q0xHmLqmin49co57RwgogMG0AqHxXzL0kRodtfDlbfT3vfLthUBcOY1f3QQAo2hgmNmmNEzU4jtUHZ-wlebmR0jCUMGOU0ujhCqLtTK2OgMbCdPs9kXlasHawMb_YFDId4z "

        try {
            val url = URL("https://fcm.googleapis.com/fcm/send")
            val connection = url.openConnection() as HttpURLConnection

            // Set the request method to POST
            connection.requestMethod = "POST"
            connection.doOutput = true

            // Replace 'YOUR_SERVER_KEY' with your FCM server key
            connection.setRequestProperty("Authorization","")
            connection.setRequestProperty("Content-Type", "application/json")

            // Create the FCM notification payload
            val payload = """
            {
                "to": "$targetToken",
                "notification": {
                    "title": "$s",
                    "body": "$s1"
                }
            }
        """.trimIndent()

            // Write the payload to the connection's output stream
            val outputWriter = OutputStreamWriter(connection.outputStream)
            outputWriter.write(payload)
            outputWriter.flush()

            // Execute the request and retrieve the response
            val responseCode = connection.responseCode
            val responseMessage = connection.responseMessage

            val response = StringBuilder()
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
            } else {
                response.append("Error: $responseCode $responseMessage")
            }

            // Handle the response as needed
            if (responseCode == HttpURLConnection.HTTP_OK) {
                println("Notification sent successfully")
                println(response.toString())
            } else {
                println("Notification sending failed")
                println(response.toString())
            }

            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showLog() {
        _currentAssignment.update { old ->
            old?.let {
                CurrentAssignmentData(
                    true,
                    it.trips,
                    it.vehicle
                )
            }

        }
    }
}