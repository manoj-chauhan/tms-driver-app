package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.drishto.driver.network.getProfileId
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.Event
import driver.models.Notice_List
import driver.ui.pages.NoticeListPage
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class   NoticeNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {

    fun getAllNotices(): List<Notice_List>? {
        val noticeList = Types.newParameterizedType(List::class.java, Notice_List::class.java)
        val adapter: JsonAdapter<List<Notice_List>> = Moshi.Builder().build().adapter(noticeList)

        val noticessurl = context.resources.getString(R.string.url_notice_list)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = noticessurl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))

                result.fold(
                    {
                        Log.d("NoticeNetRepository", "Successfully received response from backend.")
                        it
                    },
                    { error ->
                        Log.d("noticeerror", "$error")
                        EventBus.getDefault().post("AUTH_FAILED")
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)

                        when (error.response.statusCode) {
                            403 -> errorManager.getErrorDescription403(context, errorResponse)
                            404 -> errorManager.getErrorDescription404(context, "No url found")
                            500 -> errorManager.getErrorDescription500(context, "Something Went Wrong")
                        }
                        null
                    }
                )
            }
        } catch (e: Exception) {
            null
        }
    }


    fun getNoticeById(noticeId:String): Notice_List?{

        val noticesurl = context.resources.getString(R.string.url_notice_info)+noticeId


        var noticeId =""


        Log.d("NoticeNetRepository", "Fetching notice with ID: $noticeId")

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = noticesurl.httpGet()
                    .authentication().bearer(it)
                    .header("Profile-Id",noticeId)
                    .responseObject(moshiDeserializerOf(Notice_List::class.java))

                result.fold(
                    {
                    },
                    { error ->
                        Log.d("noticeerror", "$error")
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

    fun addNotice(
        name: String,
        description: String,
        date: String,
    ) {
        try {
            val addNotice = driver.models.Notice(name, description)
            Log.d("JSON", "addInstitute: $addNotice")

            val moshi = Moshi.Builder().build()

            val jsonAdapter: JsonAdapter<driver.models.Notice> =
                moshi.adapter(driver.models.Notice::class.java)
            val requestBody = jsonAdapter.toJson(addNotice)

            Log.d("anirudh", "addInstitute: $requestBody")
            val url = context.resources.getString(R.string.url_add_notice)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url)
                    .authentication().bearer(it)
                    .header("Profile-Id", "6634dc7cf684864e6508590c")
                    .jsonBody(requestBody)
                    .response()

                Log.d("TAG", "notice: $response")

                if (response.statusCode == 200) {
                } else {
                    result.fold(
                        { _ ->
                        },
                        { error ->
                            Log.d("TAG1", "addInstitute: $error")
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
            Log.d("o", "$e")

        }

    }

}
