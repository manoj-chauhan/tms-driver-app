package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.FuelManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class PostNetRepository @Inject constructor(@ApplicationContext private val context: Context, private val errorManager: ErrManager) {

    fun uploadPost(image: File){
        try {
            val url = context.resources.getString(R.string.url_get_mediaId)
            Log.d("upload", "upload Post url: $url")
            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url)
                    .body(image)
                    .response()

                if (response.statusCode == 200) {
                    Log.d("Funciton", "Upload post : ")
                } else {
                    result.fold(
                        {
                        },
                        { error ->
                            Log.d("Hie", "uploadPost: ${error.response.statusCode} ")
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
        }
    }

}