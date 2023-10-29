package com.samrish.driver.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.models.AssignmentHistory
import com.samrish.driver.models.History
import com.samrish.driver.network.getAccessToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    private val  _historyDetail: MutableStateFlow<AssignmentHistory?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<AssignmentHistory?> =  _historyDetail.asStateFlow()

    private var currentPage: Int = 0

    fun incrementPage() {
        currentPage++
    }
    fun fetchHistoryDetail(context: Context){
        val channel4= Channel<MutableList<History>>()

        viewModelScope.launch(Dispatchers.IO) {
            val historyurl = context.resources.getString(R.string.url_trip_history)  + "assignmentHistory?pageno="+ currentPage
            Log.d("TAG", "fetchHistoryDetail:$historyurl ")

            val  historyList = Types.newParameterizedType(MutableList::class.java, History::class.java)
            val history: JsonAdapter<MutableList<History>> = Moshi.Builder().build().adapter( historyList)

            getAccessToken(context)?.let {
                val (request1, response1, result) = historyurl.httpGet()
                    .authentication().bearer(it)
                    .response(moshiDeserializerOf(history))

                result.fold(
                    {
                            historyDetail -> channel4.send(historyDetail)
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
            val tripDetail = channel4.receive();

            _historyDetail.update { _ ->
                AssignmentHistory(
                    tripDetail
                )
            }
            Log.i("tag", "$tripDetail")
        }

    }
}
