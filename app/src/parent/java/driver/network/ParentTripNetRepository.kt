package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.ParentTrip
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ParentTripNetRepository@Inject constructor(
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
                Log.d("TAG", "fetchActiveTrips: $it")
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                        Log.d("TAG", "fetchActiveTrips: $result")
                result.fold(
                    {
                    },
                    {
                        EventBus.getDefault().post("AUTH_FAILED")
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }
}