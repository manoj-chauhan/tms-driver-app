package driver.network

import android.content.Context
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
import driver.models.Events
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class EventNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {

    fun getAllEvents():List<Events>?{
        val eventsList = Types.newParameterizedType(List::class.java, Events::class.java)
        val adapter: JsonAdapter<List<Events>> = Moshi.Builder().build().adapter(eventsList)

        val eventsurl = context.resources.getString(R.string.url_get_all_events)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = eventsurl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))

                result.fold(
                    {
                    },
                    { error ->
                        EventBus.getDefault().post("AUTH_FAILED")
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)

                        if (error.response.statusCode == 403) {
                            errorManager.getErrorDescription403(context, errorResponse)
                        }

                        if (error.response.statusCode == 404) {
                            errorManager.getErrorDescription404(context, "No url found")
                        }

                        if (error.response.statusCode == 500) {
                            errorManager.getErrorDescription500(context, "Something Went Wrong")
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