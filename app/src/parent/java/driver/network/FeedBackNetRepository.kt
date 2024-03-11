package driver.network

import android.content.Context
import android.widget.Toast
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.Feedback
import javax.inject.Inject

class FeedBackNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {

    fun sendFeedback(userId:Int, message:String){
        try {
            val feedback = Feedback(userId , message)
                val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Feedback> = moshi.adapter(Feedback::class.java)
            val requestBody = jsonAdapter.toJson(feedback)

            val url = context.resources.getString(R.string.url_send_feedback)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .jsonBody(requestBody)
                    .response()

                if (response.statusCode == 200) {
                } else {
                    result.fold(
                        { _ ->
                        },
                        { error ->
                            if (error.response.statusCode == 401) {
                                errorManager.getErrorDescription(context)
                            }

                            val errorResponse = error.response.data.toString(Charsets.UTF_8)
                        }
                    )
                }
            }

            Toast.makeText(
                context,
                "FeedBack Sent Successfully.",
                Toast.LENGTH_SHORT
            ).show()

        } catch(e:Exception){

        }


    }
}