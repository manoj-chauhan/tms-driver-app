package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.drishto.driver.network.getProfileId
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.Event
import driver.models.EventRegistration
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class EventNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {

    fun getAllEvents():List<Event>?{
        val eventsList = Types.newParameterizedType(List::class.java, Event::class.java)
        val adapter: JsonAdapter<List<Event>> = Moshi.Builder().build().adapter(eventsList)

        val eventsurl = context.resources.getString(R.string.url_get_all_public_events)+"?pageno=0"

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = eventsurl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))

                result.fold(
                    {
                        Log.d("EventNetRepository", "Successfully received response from backend.")
                    },
                    { error ->
                        Log.d("eventerror", "$error")
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
    fun addEvent(event: EventRegistration, profileId: String)
        {
            try {

                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<EventRegistration> = moshi.adapter(EventRegistration::class.java)
                val requestBody = jsonAdapter.toJson(event)

                Log.d("json check", requestBody)


                val url = context.resources.getString(R.string.url_add_event)




                getAccessToken(context)?.let { token ->
                    val fuelManager = FuelManager()
                    val (_, response, result) = fuelManager.post(url)
                        .authentication().bearer(token)
                        .header("Profile-Id", profileId)
                        .jsonBody(requestBody)
                        .response()

                    if (response.statusCode == 200) {



                    } else {

                        result.fold(
                            { _ ->

                            },
                            { error ->
                                Log.d("Error", "addEvent: $error")
                                Log.d("Error", "addEvent: ${error.response.statusCode}")

                                if (error.response.statusCode == 401 ) {
                                    errorManager.getErrorDescription(context)
                                }

                                val errorResponse = error.response.data.toString(Charsets.UTF_8)

                                if (error.response.statusCode == 403 ) {
                                    errorManager.getErrorDescription403(context, errorResponse)
                                }

                                if (error.response.statusCode == 404 ) {
                                    errorManager.getErrorDescription404(context, "No url found")
                                }

                                if(error.response.statusCode == 500){
                                    errorManager.getErrorDescription500(context, "Something Went Wrong")
                                }
                            }
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("AddEventNetRepository", "Error adding event", e)
            }

        }

    fun getEventById(eventId:String):Event?{

        val eventsurl = context.resources.getString(R.string.url_get_event_by_ID)+eventId


        var profileId =""
        getProfileId(context).let {
            if (it != null) {
                profileId = it
            }
        }

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = eventsurl.httpGet()
                    .authentication().bearer(it)
                    .header("Profile-Id",profileId)
                    .responseObject(moshiDeserializerOf(Event::class.java))

                result.fold(
                    {
                    },
                    { error ->
                        Log.d("eventerror", "$error")
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
