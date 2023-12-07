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
import java.util.concurrent.LinkedBlockingQueue
import javax.inject.Inject

class TelemetryManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val telemetryNetRepository: TelemetryNetRepository,
    private val telemetryRepository: TelemetryRepository
) : TelemetryManager {

    private val telemetryQueue: LinkedBlockingQueue<Telemetry> = LinkedBlockingQueue()
    override suspend fun sendMatrix(telemetry: Telemetry) {

        telemetryQueue.offer(telemetry)
        Log.d("Queue", "sendMatrix: $telemetryQueue")
        startTelemetryProducer()
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
                t.isDataLoaded
            )
        }.toList()
    }

    override fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun startTelemetryProducer() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                Log.d("Queue", "startTelemetryProducer: $telemetryQueue")
                val telemetry = telemetryQueue.take()
                Log.d("Queue item to take", "startTelemetryProducer: $telemetry ")
                if (isNetworkAvailable(context)) {
                    saveTelemetryToDatabase(telemetry)
                    sendTelemetryToServer(telemetry)
                } else {
                    Log.d("Queue item to take in Network off", "startTelemetryProducer: $telemetry ")
                    saveTelemetryToDatabase(telemetry)
                }
            }
        }
    }

    private fun saveTelemetryToDatabase(telemetry: Telemetry) {
        CoroutineScope(Dispatchers.IO).launch {
            telemetryRepository.insertLocation(
                com.samrish.driver.database.Telemetry(
                    telemetry.latitude,
                    telemetry.longitude,
                    telemetry.time,
                    false
                )
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

}