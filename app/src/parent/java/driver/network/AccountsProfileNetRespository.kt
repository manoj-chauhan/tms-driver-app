package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.AccountProfile
import driver.models.InstituteInfo
import driver.models.SchoolDetails
import driver.models.StudentDetails
import driver.models.TeacherDetails
import driver.models.UserProfile
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class AccountsProfileNetRespository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {

    fun fetchAccountProfile(): List<AccountProfile>? {
        val accountsList =
            Types.newParameterizedType(List::class.java, AccountProfile::class.java)
        val adapter: JsonAdapter<List<AccountProfile>> =
            Moshi.Builder().build().adapter(accountsList)

        val profileListurl = context.resources.getString(R.string.url_get_all_profiles)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = profileListurl.httpGet()
                    .authentication().bearer(it)
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

    fun addProfile(
        type:String,
        name: String, anchor: String, standard: String,
        section: String,
        session: String,
        instituteId: String,
        description: String,
        childClass:String,
        schoolName: String
    ) {

        try {
            val profile = when (type) {
                "Student" -> {
                    UserProfile(name, type, anchor,studentDetails = StudentDetails(childClass, section, session, "123445662342"))
                }
                "Teacher" -> {
                    val institute = InstituteInfo("instituteId",session)
                    UserProfile(name, type, anchor, parentDetails =  TeacherDetails(description, institute))
                }
                "School" -> {
                    UserProfile(name, type, anchor, schoolDetails = SchoolDetails(schoolName))
                }
                else -> null
            }
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<UserProfile> = moshi.adapter(UserProfile::class.java)
            val requestBody = jsonAdapter.toJson(profile)

            Log.d("User profile", "Profile: $requestBody")

            val url = context.resources.getString(R.string.url_add_profile)

            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                    .jsonBody(requestBody)
                    .response()

                if (response.statusCode == 200) {
                } else {
                    result.fold(
                        { _ ->
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
            }

        } catch (e: Exception) {

        }


    }
}