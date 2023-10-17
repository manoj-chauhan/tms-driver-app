package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.services.getAccessToken
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@JsonClass(generateAdapter = true)
data class UserProfile(
    val name: String, 
    val username: String
)

class UserProfileViewModel : ViewModel() {
    private val _userDetails: MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val userDetail: StateFlow<UserProfile?> = _userDetails.asStateFlow()

    fun userDetail(context: Context){
        val channel1 = Channel<UserProfile>()
        val url = context.resources.getString(R.string.url_profile)
        viewModelScope.launch(Dispatchers.IO)
        {
            getAccessToken(context)?.let {
                val (request1, response1, userResult) = url.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(UserProfile::class.java))

                userResult.fold(
                    { userDetail ->
                        channel1.send(userDetail)
                    },
                    { error ->
                        Log.e(
                            "Fuel",
                            "Error $error"
                        )
                    }
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {

            val tripDetail = channel1.receive();

            _userDetails.update { _ ->
                UserProfile(
                    tripDetail.name,
                    tripDetail.username
                )
            }


            Log.d("TAG", "userDetail: ${tripDetail.name}")
        }

    }

}