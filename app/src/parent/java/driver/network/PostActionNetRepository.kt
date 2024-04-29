package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.Comment
import driver.models.Comments
import javax.inject.Inject

class PostActionNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun sendComment(postId: String, comment: String) {
        try {

            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Comment> = moshi.adapter(Comment::class.java)
            val sendComment = Comment(comment)
            val requestBody = jsonAdapter.toJson(sendComment)

            getAccessToken(context)?.let {

                val url = context.resources.getString(R.string.url_upload_comment) + postId

                val (request1, response, result) = url.httpPost()
                    .authentication().bearer(it)
                    .body(requestBody)
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

    fun getComments(postId: String): List<Comments>?{

        val comment = Types.newParameterizedType(List::class.java, Comments::class.java)
        val adapter: JsonAdapter<List<Comments>> = Moshi.Builder().build().adapter(comment)

        return try {
            getAccessToken(context)?.let {
                val url = context.resources.getString(R.string.url_get_comments_posts) + postId +"/comment"
                val (request, response, result) = url.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))


                if (response.statusCode == 200) {
                } else {
                    result.fold(
                        {
                        },
                        { error ->
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
                result.get()
            }
        } catch (e: Exception) {
            null
        }

    }
}