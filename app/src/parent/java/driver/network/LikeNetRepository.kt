package driver.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import driver.models.LikeRequest

class LikeNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun likePost(postId: Int) {
        try {

            val likeRequest = LikeRequest(postId)


            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<LikeRequest> = moshi.adapter(LikeRequest::class.java)
            val requestBody = jsonAdapter.toJson(likeRequest)


            val url = context.resources.getString(R.string.url_likePost)


            val fuelManager = FuelManager()
            val (_, response, result) = fuelManager.post(url).jsonBody(requestBody).response()


            if (response.statusCode == 200) {
                Toast.makeText(context, "Post Liked Successfully", Toast.LENGTH_SHORT).show()
            } else {
                handleError(response, errorManager)
            }

        } catch (e: Exception) {
            Log.e("LikeNetRepository", "Error liking post", e)
            Toast.makeText(context, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleError(
        response: Response,
        errorManager: ErrManager
    ) {
        val errorCode = response.statusCode
        val errorResponse = response.data.toString(Charsets.UTF_8)

        when (errorCode) {
            401 -> errorManager.getErrorDescription(context)
            403 -> errorManager.getErrorDescription403(context, errorResponse)
            404 -> errorManager.getErrorDescription404(context, "No URL found")
            500 -> errorManager.getErrorDescription500(context, "Something went wrong")
            else -> Log.e("LikeNetRepository", "Unknown error: $errorCode")
        }


        Toast.makeText(context, "An error occurred. Status code: $errorCode", Toast.LENGTH_SHORT).show()
    }
}
