package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpPost
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostActionNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
)  {
    fun sendComment(postId:String){
        try {

            getAccessToken(context)?.let {

                val url = context.resources.getString(R.string.url_upload_comment)

                val (request1, response, result) = url.httpPost()
                    .authentication().bearer(it)
                    .response()

                if (response.statusCode == 200) {
                } else {
                    result.fold(
                        { _ ->
                        },
                        { error ->
                            Log.d("TAG", "uploadPosts: $error")
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
                }

            }
        } catch (e: Exception) {

        }
    }
}