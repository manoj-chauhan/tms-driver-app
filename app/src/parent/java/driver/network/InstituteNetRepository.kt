package driver.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.AddressInfo
import driver.models.ContactList
import driver.models.InstituteAddInfo
import javax.inject.Inject


class InstituteNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun addInstitute(
        name: String,
        contacts: List<ContactList>,
        description: String,
        facilities: List<String>,
        address: String,
        state: String,
        city: String,
    ) {
        try {
            val addressInfo=AddressInfo(address,city,state)
            val addInstituteRequest = InstituteAddInfo(name, contacts, description, addressInfo, facilities)
            Log.d("JSON", "addInstitute: $addInstituteRequest")

            val moshi = Moshi.Builder().build()

            val jsonAdapter: JsonAdapter<InstituteAddInfo> = moshi.adapter(InstituteAddInfo::class.java)
            val requestBody = jsonAdapter.toJson(addInstituteRequest)

            Log.d("anirudh", "addInstitute: $requestBody")
            val url = context.resources.getString(R.string.url_addInstitute)

            val fuelManager = FuelManager()
            val (_, response, result) = fuelManager.post(url).jsonBody(requestBody)
                .response()

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
            Toast.makeText(
                context,
                "Institue Added Successfully",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            Log.d("o", "$e")

        }

    }
}
