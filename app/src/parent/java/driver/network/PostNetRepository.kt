package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.github.kittinunf.fuel.core.FuelManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {

    suspend fun uploadPost(image: ByteArray): String= withContext(Dispatchers.IO) {
        val url = context.resources.getString(R.string.url_get_mediaId)
        try {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url)
                    .body(image)
                    .responseString()

                result.fold(
                    {
                    },
                    { error ->
                        Log.d("Hie", "uploadPost: $error ")
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
        } catch (e: Exception) {
            "Error Handling Post"
        }
    }
}