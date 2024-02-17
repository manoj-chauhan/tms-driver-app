package com.drishto.driver.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.R
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class UserProfile(
    val name: String, 
    val userName: String,
    val companiesList: List<CompanyPositions>,
    var authProvider:String,
    val id:Int
)
@JsonClass(generateAdapter = true)
data class CompanyPositions(
    val companyCode: String,
    val companyName: String,
    val roles: List<String>
)
@HiltViewModel
class UserProfileViewModel @Inject constructor(private val userProfileManager: com.drishto.driver.usermgmt.UserManager) : ViewModel(){
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
                    tripDetail.userName,
                    tripDetail.companiesList,
                    tripDetail.authProvider,
                    tripDetail.id
                )
            }


            Log.d("TAG", "userDetail: ${tripDetail.name}")
        }

    }

    fun editUserName(name:String, context:Context){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userProfileManager.editUserName(name)
                userDetail(context)
            } catch (e: Exception) {
            }
        }
    }

    fun uploadImage(image: ByteArray?){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("VIewmodel", "uploadImage: ")

                if (image != null) {
                    Log.d("New", "uploadImage: ")

                    userProfileManager.uploadPhoto(image)
                }
            }catch (e:Exception){

            }
        }
    }

}