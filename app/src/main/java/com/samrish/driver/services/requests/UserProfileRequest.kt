package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.Company
import com.samrish.driver.models.Trip
import com.samrish.driver.models.UserProfile
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class UserProfileRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<UserProfile>,
    errorListener: Response.ErrorListener
) : GenericRequest<UserProfile>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): UserProfile {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )
        val t = JSONObject(responseBody)
        return UserProfile(
            id = t.getInt("id"),
            name = t.getString("name"),
            authProvider = t.optString("primaryContact",""),
            userName = t.optString("secondaryContact", ""),
            companies = parseCompany(t.getJSONArray("companiesList"))
        )

    }

    private fun parseCompany(companies: JSONArray): List<Company> {

        val mutableList = mutableListOf<Company>()

        for (i in 0 until companies.length()) {
            val comp = companies.getJSONObject(i)
            mutableList.add(Company(
                id = comp.optInt("companyId",0),
                code = comp.getString("companyCode"),
                name = comp.getString("companyName"),
                roles = parseRoles(comp.getJSONArray("roles"))
            ))
        }
        return mutableList
    }

    private fun parseRoles(roles: JSONArray): List<String> {
        val mutableList = mutableListOf<String>()
        for (i in 0 until roles.length()) {
            mutableList.add(roles.getString(i))
        }
        return mutableList
    }


}