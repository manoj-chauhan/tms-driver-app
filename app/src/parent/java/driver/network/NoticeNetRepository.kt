package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.ui.pages.Notice
import javax.inject.Inject

class NoticeNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {

    suspend fun getAllNotices(): List<Notice>? {
        val moshi = Moshi.Builder().build()
        val noticeListType = Types.newParameterizedType(List::class.java, Notice::class.java)
        val jsonAdapter: JsonAdapter<List<Notice>> = moshi.adapter(noticeListType)

//        val url = context.resources.getString(R.string.url_addInstitute)

        return try {
            null

//            getAccessToken(context)?.let { token ->
//                val fuelManager = FuelManager()
//                val (_, response, result) = fuelManager.get(url)
//                    .authentication().bearer(token)
//                    .responseObject(moshiDeserializerOf(jsonAdapter))
//
//
//                result.fold(
//                    { it },
//                    { error ->
//                        handleErrors(response)
//                        null
//                    }
//                )
//            }
        }
        catch (e: Exception) {
            Log.e("NoticeNetRepository", "Error fetching notices", e)
            null
        }
    }


    private fun handleErrors(response: Response) {
        val errorCode = response.statusCode
        val errorMessage = response.data.toString(Charsets.UTF_8)

        when (errorCode) {
            401 -> errorManager.getErrorDescription(context)
            403 -> errorManager.getErrorDescription403(context, errorMessage)
            404 -> errorManager.getErrorDescription404(context, "No URL found")
            500 -> errorManager.getErrorDescription500(context, "Server error")
            else -> Log.e("NoticeNetRepository", "Unhandled error: $errorCode")
        }
    }

}