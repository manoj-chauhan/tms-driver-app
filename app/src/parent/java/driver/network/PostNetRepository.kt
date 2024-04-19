package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.PostUpload
import driver.models.PostsFeed
import driver.models.UploadPosts
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class PostNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun getMediaID(image: ByteArray): String {
        val url = context.resources.getString(R.string.url_get_mediaId)
        return try {
            val fuelManager = FuelManager()
            val (_, response, result) = fuelManager.post(url)
                .body(image)
                .responseString()

            result.fold(
                {
                },
                { error ->
                    if (error.response.statusCode == 401) {
                        errorManager.getErrorDescription(context)
                    }

                    val errorResponse = error.response.data.toString(Charsets.UTF_8)

                    Log.d("Hie", "uploadPost: $errorResponse ")
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

    fun uploadPosts(media: List<PostUpload>, message: String) {
        try {
            val postUploadRequest = UploadPosts("661e625812e20f273847eb04", media, message)

            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<UploadPosts> = moshi.adapter(UploadPosts::class.java)
            val requestBody = jsonAdapter.toJson(postUploadRequest)

            Log.d("Request body", "uploadPosts: $requestBody")

            val url = context.resources.getString(R.string.url_upload_post)

            val (request1, response, result) = url.httpPost()
                .jsonBody(requestBody)
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
        } catch (e: Exception) {

        }
    }

    fun getAllFeeds(): List<PostsFeed>? {
        val postsFeeds = Types.newParameterizedType(List::class.java, PostsFeed::class.java)
        val adapter: JsonAdapter<List<PostsFeed>> = Moshi.Builder().build().adapter(postsFeeds)

        val postUrl = context.resources.getString(R.string.url_get_all_posts)

        return try {
            val (_, _, result) = postUrl.httpGet()
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

        } catch (e: Exception) {
            null
        }

    }
}