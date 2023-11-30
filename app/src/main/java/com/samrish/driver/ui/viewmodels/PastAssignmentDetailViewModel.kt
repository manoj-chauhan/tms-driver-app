package com.samrish.driver.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.errormgmt.ErrManager
import com.samrish.driver.models.Documents
import com.samrish.driver.models.PastAssignmentDetailData
import com.samrish.driver.models.TripDetail
import com.samrish.driver.network.getAccessToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PastAssignmentDetailViewModel @Inject constructor(private  val errorManager: ErrManager, application: Application): AndroidViewModel(application)  {
    private val _pastassignmentDetail: MutableStateFlow<PastAssignmentDetailData?> = MutableStateFlow(null)
    val pastassignmentDetail: StateFlow<PastAssignmentDetailData?> = _pastassignmentDetail.asStateFlow()
    var status :String ?= null

    fun fetchAssignmentDetail(context: Context, tripId:Int, tripCode:String, operatorId:Int){

        Log.i("","$tripId")

        val channel1 = Channel<TripDetail>()
        val channel4= Channel<MutableList<Documents>>()


        viewModelScope.launch(Dispatchers.IO) {
            val documentsurl = context.resources.getString(R.string.url_trip_document) + tripId


            val  documentList = Types.newParameterizedType(MutableList::class.java, Documents::class.java)
            val documents: JsonAdapter<MutableList<Documents>> = Moshi.Builder().build().adapter( documentList)

            getAccessToken(context)?.let {
                val (request1, response1, result) = documentsurl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .response(moshiDeserializerOf(documents))

                Log.d("TAG", "fetchAssignmentDetail: $request1")
                result.fold(
                    {
                            documentsDetail -> channel4.send(documentsDetail)
                        Log.d("Trip Detail", "fetchAssignmentDetail: $documentsDetail")

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
            val tripDetailUrl = context.resources.getString(R.string.url_trips_detail) + tripCode
            getAccessToken(context)?.let {
                val (request1, response1, result1) = tripDetailUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(TripDetail::class.java))

                result1.fold(
                    {
                            tripDetail -> channel1.send(tripDetail)
                        status = tripDetail.status
                        Log.d("Trip Detail", "trip detail: $status")

                        Log.d("Trip Detail", "trip detail: $tripDetail, $tripDetailUrl")
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

            Log.d("Ended", "here is assignment details ")
            viewModelScope.launch(Dispatchers.IO) {
                val tripDetail = channel1.receive();
                val documents = channel4.receive()

                Log.d("TAG", "fetchAssignmentDetail:${pastassignmentDetail.value?.tripDetail?.status} ")
                _pastassignmentDetail.update { _ ->
                    PastAssignmentDetailData(
                        tripDetail,
                        documents,
                        true
                    )
                }
                Log.i("Fuel", "Response1: $tripDetail, $documents")
            }
    }
}