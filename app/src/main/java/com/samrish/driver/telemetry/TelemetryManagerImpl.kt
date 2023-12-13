package com.samrish.driver.telemetry

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.samrish.driver.auth.AuthManager
import com.samrish.driver.database.TelemetryRepository
import com.samrish.driver.models.Telemetry
import com.samrish.driver.network.TelemetryNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.LinkedBlockingQueue
import javax.inject.Inject

class TelemetryManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val telemetryNetRepository: TelemetryNetRepository,
    private val telemetryRepository: TelemetryRepository
) : TelemetryManager {

    private var isConsumerRunning :Boolean = false
    private val telemetryQueue: LinkedBlockingQueue<com.samrish.driver.database.Telemetry> = LinkedBlockingQueue()
    override suspend fun sendMatrix(telemetry: Telemetry) {

        val json = com.samrish.driver.database.Telemetry(telemetry.latitude, telemetry.longitude, telemetry.time, false, telemetry.deviceIdentifier)

        saveTelemetryToDatabase(json)
        telemetryQueue.offer(json)
//        telemetryRepository.insertLocation(tel)
//        Log.d(
//            "Size Of New List",
//            "sentTelemetry: ${telemetryRepository.getTelemetryWithFalseStatus().size} "
//        )
//        if (isNetworkAvailable(context)) {
//            withContext(Dispatchers.IO) {
//                var telemetryWithFalseStatus: List<com.samrish.driver.database.Telemetry> =
//                    telemetryRepository.getTelemetryWithFalseStatus()
//                if (telemetryWithFalseStatus.isNotEmpty()) {
//                    telemetryWithFalseStatus.forEach { metry ->
//                        val tele = Telemetry(
//                            telemetry.deviceIdentifier,
//                            metry.latitude,
//                            metry.longitude,
//                            metry.time
//                        )
//                        try {
//                            telemetryNetRepository.sentTelemetry(tele)
//                            Log.d("Matrix Id", "sendMatrix: ${metry.id}")
//                            telemetryRepository.updateTelemetryStatus(metry.id, true)
//                            telemetryWithFalseStatus = telemetryRepository.getTelemetryWithFalseStatus()
//                            Log.d(
//                                "Telemetry after one false Status ",
//                                "sendMatrix: $telemetryWithFalseStatus"
//                            )
//                        } catch (e: Exception) {
//                            Log.d("", "")
//                        }
//                    }
//                }
//            }
//        } else {
//            Log.d("No Internet ", "No sending Matrix: $tel")
//        }

    }

    override suspend fun getTelemetry(): List<com.samrish.driver.database.Telemetry> {
        return telemetryRepository.loadMatrices().asSequence().map { t ->
            com.samrish.driver.database.Telemetry(
                t.latitude,
                t.longitude,
                t.time,
                t.isDataLoaded,
                t.deviceIdentifier
            )
        }.toList()
    }

    override fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun startTelemetryConsumer(list : List<com.samrish.driver.database.Telemetry>,onConsumerStarted:()->Unit) {
//        CoroutineScope(Dispatchers.IO).launch {
//            while (true) {
//                Log.d("Queue", "startTelemetryProducer: $telemetryQueue")
////                val telemetry = telemetryQueue.take()
//                val telemetry = telemetryQueue.peek() ?: break
//
//                Log.d("Queue item to take", "startTelemetryProducer: $telemetry ")
//                if (isNetworkAvailable(context)) {
//                    Log.d("Network", "startTelemetryConsumer: $telemetry ")
//                    sendTelemetryToServer(telemetry)
//                    telemetryQueue.poll()
//                } else {
//                    Log.d("Queue item to take in Network off", "startTelemetryProducer: $telemetry ")
//                }
//            }
//        }

        CoroutineScope(Dispatchers.IO).launch {
            telemetryQueue.addAll(list)
            withContext(Dispatchers.Main) {
                onConsumerStarted()
            }
            isConsumerRunning = true
            while (isConsumerRunning) {
                Log.d("Queue", "startTelemetryProducer: $telemetryQueue")
                val telemetry = telemetryQueue.peek()

                Log.d("Queue item to take", "startTelemetryProducer: $telemetry ")

                if (isNetworkAvailable(context)) {
                    Log.d("Network", "startTelemetryConsumer: $telemetry ")
                    val jsonTelemetry = telemetry?.deviceIdentifier?.let { Telemetry(it, telemetry.latitude, telemetry.longitude, telemetry.time) }
                    if (jsonTelemetry != null) {
                        sendTelemetryToServer(jsonTelemetry)
                    }
                    telemetryQueue.poll()
                } else {
                    Log.d(
                        "Queue item to take in Network off",
                        "startTelemetryProducer: $telemetry "
                    )
                }
            }

        }

    }

    private fun saveTelemetryToDatabase(telemetry: com.samrish.driver.database.Telemetry) {
        CoroutineScope(Dispatchers.IO).launch {
            telemetryRepository.insertLocation(
                telemetry
            )
        }
    }

    private suspend fun sendTelemetryToServer(telemetry: Telemetry) {
        try {
            telemetryNetRepository.sentTelemetry(telemetry)
            val id = telemetryRepository.getTelemetryId(telemetry.latitude, telemetry.longitude, telemetry.time)
            telemetryRepository.updateTelemetryStatus(id, true)
        } catch (e: Exception) {
            Log.e("TelemetryManagerImpl", "Error sending telemetry", e)
        }
    }

    override fun stopTelemetryConsumer() {
        isConsumerRunning = false
    }

}