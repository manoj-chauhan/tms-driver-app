package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.EducationList
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class EducationNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun fetchEducationList(): List<EducationList>? {
        val EducationListType =
            Types.newParameterizedType(List::class.java, EducationList::class.java)
        val adapter: JsonAdapter<List<EducationList>> =
            Moshi.Builder().build().adapter(EducationListType)

        val EducationListUrl = context.resources.getString(R.string.education_list)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = EducationListUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {
                    },
                    { error ->
                        Log.d("Error", "fetchEducationList: $error")
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

