package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.PlaceInfo
import javax.inject.Inject

class PlaceInfoNetRepository @Inject constructor( @ApplicationContext private val context: Context, private val errorManager: ErrManager) {
    fun fetchPlaceLatitudeLongitude(placeCode:String): PlaceInfo {
        val tripAssignmentUrl = context.resources.getString(R.string.url_place_info)+placeCode

        return try {
            getAccessToken(context)?.let {
                val (request1, response1, result1) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", 1)
                    .responseObject(moshiDeserializerOf(PlaceInfo::class.java))
                result1.fold(
                    { tripDetail ->
                        tripDetail
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                        throw Exception("Error fetching trip details")
                    }
                )
            }
                ?: throw Exception("Access token is null") // Throw an exception if access token is null
        } catch (e: Exception) {
            Log.e(
                "Fuel",
                "Exception $e"
            )
            throw Exception("Exception fetching trip details")
        }
    }
}