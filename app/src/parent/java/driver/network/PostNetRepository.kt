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
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.CommentPost
import driver.models.PostFeedResult
import driver.models.PostUpload
import driver.models.PostsFeed
import driver.models.Scope
import driver.models.UploadPosts
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class PostNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun getMediaID(image: ByteArray, mimeType: String): String {
        val url = context.resources.getString(R.string.url_get_mediaId)
        return try {
            getAccessToken(context)?.let {

                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url)
                    .authentication().bearer(it)
                    .body(image)
                    .header("content-type", mimeType)
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
            } ?: throw Exception("Access token is null")
        } catch (e: Exception) {
            "Error Handling Post"
        }
    }

    fun uploadPosts(media: List<PostUpload?>, message: String, profileId: String) {
        try {
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<UploadPosts> = moshi.adapter(UploadPosts::class.java)

            getAccessToken(context)?.let {
                val scope= Scope("Public")
                val postUploadRequest = UploadPosts(profileId, media, message, scope)
                val requestBody = jsonAdapter.toJson(postUploadRequest)


                Log.d("Request body", "uploadPosts: $requestBody")

                val url = context.resources.getString(R.string.url_upload_post)

                val (request1, response, result) = url.httpPost()
                    .authentication().bearer(it)
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

            }
        } catch (e: Exception) {

        }
    }

    fun getAllFeeds(context: Context): PostFeedResult {
        val postsFeeds = Types.newParameterizedType(List::class.java, PostsFeed::class.java)
        val adapter: JsonAdapter<List<PostsFeed>> = Moshi.Builder().build().adapter(postsFeeds)

        val postUrl = context.resources.getString(R.string.url_get_all_posts)
        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = postUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {posts ->
                        PostFeedResult.Success(posts)
                    },
                    { error ->
                        EventBus.getDefault().post("AUTH_FAILED")
                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
                        val errorMessage = when (error.response.statusCode) {
                            401 -> errorManager.getErrorRouteDescription401(context, errorResponse)
                            403 -> errorManager.getErrorRouteDescription403(context, errorResponse)
                            404 -> errorManager.getErrorRouteDescription404(context, "No URL found")
                            500 -> errorManager.getErrorRouteDescription500(context, "Something Went Wrong")
                            else -> "Unknown error"
                        }
                        PostFeedResult.Error(errorMessage)
                    }
                )
            } ?: PostFeedResult.Error("Access token is null")
        } catch (e: Exception) {
            PostFeedResult.Error("Exception occurred: ${e.message}")
        }
    }

    fun getPostDetail(context: Context, postId: String): PostsFeed? {

        val postUrl = context.resources.getString(R.string.url_get_post_detail) + postId
        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = postUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(PostsFeed::class.java))

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

    fun getCommentsByPost(context: Context, postId: String): List<CommentPost>? {
        val commentsPost = Types.newParameterizedType(List::class.java, CommentPost::class.java)
        val adapter: JsonAdapter<List<CommentPost>> = Moshi.Builder().build().adapter(commentsPost)

        val commentUrl =
            context.resources.getString(R.string.url_get_comments_posts) + postId + "/comments"
        var profile = ""
        getProfileId(context)?.let {
            profile = it
        }

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = commentUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Profile-Id", profile)
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