package driver.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.ParentPastTrip
import driver.models.ParentTrip
import driver.models.ParentTripDetail
import driver.models.ProcessedPoints
import driver.models.currentDriverLocation
import driver.models.point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class ParentTripNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun fetchActiveTrips(): List<ParentTrip>? {
        val assignedTripType =
            Types.newParameterizedType(List::class.java, ParentTrip::class.java)
        val adapter: JsonAdapter<List<ParentTrip>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.url_parent_trip)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {
                    },
                    {error->
                        Log.d("Error", "fetchActiveTrips: $error")
                        EventBus.getDefault().post("AUTH_FAILED")
                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription401(context, errorResponse)
                        }

                        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
                            errorManager.handleErrorResponse(context, errorResponse)
                        }
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }
    fun fetchTripRouteCoor(operatorId: Int, tripCode: String): List<point> {
        val assignedTripType = Types.newParameterizedType(List::class.java, point::class.java)
        val adapter: JsonAdapter<List<point>> = Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl =
            context.resources.getString(R.string.url_trip_lat_lon) + tripCode

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {
                        it ?: emptyList() // Return empty list if the result is null
                    },
                    {error ->
                        EventBus.getDefault().post("AUTH_FAILED")
                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription401(context, errorResponse)
                        }

                        if (error.response.statusCode == 500) {
                            errorManager.getErrorDescription500(context, errorResponse)
                        }
                        emptyList()
                    }
                )
            } ?: emptyList() // Return empty list if access token is null
        } catch (e: Exception) {
            emptyList() // Return empty list in case of any exception
        }
    }

    fun fetchTripProcessed(operatorId: Int, tripCode: String): List<ProcessedPoints>? {
        val tripCoor = Types.newParameterizedType(List::class.java, ProcessedPoints::class.java)
        val adapter: JsonAdapter<List<ProcessedPoints>> = Moshi.Builder().build().adapter(tripCoor)

        val tripProcessedCoordinates =
            context.resources.getString(R.string.url_trip_processed) + tripCode
        val handler = Handler(Looper.getMainLooper())


        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripProcessedCoordinates.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {
                    },
                    {error->
                        EventBus.getDefault().post("AUTH_FAILED")
                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription401(context, errorResponse)
                        }

                        else if (error.response.statusCode == 500) {
                            errorManager.getErrorDescription500(context, errorResponse)
                        }else {
                            handler.post {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchPastTrips(): List<ParentPastTrip>? {
        val pastTrip = Types.newParameterizedType(List::class.java, ParentPastTrip::class.java)
        val adapter: JsonAdapter<List<ParentPastTrip>> = Moshi.Builder().build().adapter(pastTrip)

        val tripPast =
            context.resources.getString(R.string.url_past_trips)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripPast.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {
                    it
                    },
                    {error->
                        EventBus.getDefault().post("AUTH_FAILED")
                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription401(context, errorResponse)
                        }

                        if (error.response.statusCode == 500) {
                            errorManager.getErrorDescription500(context, errorResponse)
                        }
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchParentTripDetail(passengerTripId: Int,navHostController: NavHostController): ParentTripDetail? {
        val tripDetailUrl = context.resources.getString(R.string.url_trip_detail) + passengerTripId
        val handler = Handler(Looper.getMainLooper())

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripDetailUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(ParentTripDetail::class.java))
                result.fold(
                    {
                        it
                    },
                    {error->
                        EventBus.getDefault().post("AUTH_FAILED")
                        handler.post {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }

                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchDriverLiveLoc(passengerTripId: Int): currentDriverLocation? {
        val driverLiveUrl = context.resources.getString(R.string.url_driver_location) + passengerTripId

        val handler = Handler(Looper.getMainLooper())

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = driverLiveUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(currentDriverLocation::class.java))
                result.fold(
                    {
                        it
        Log.d("DRIVER IS HERE", "fetchDriverLiveLoc: $result")
                    },
                    {error->
                            handler.post {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }
}