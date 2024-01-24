package com.drishto.driver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class OtpVerification: BroadcastReceiver() {

    var sms:OtpReceiverListener ?= null
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "onReceive: ")
            if(SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action){
                val bundle= intent.extras
                if(bundle != null){
                    val status : Status = bundle.get(SmsRetriever.EXTRA_STATUS) as Status
                    when (status.statusCode) {
                        CommonStatusCodes.SUCCESS -> {
                            // Extract the OTP from the SMS message
                            val message =bundle.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)

                            Log.d("Message", "onReceive: $message")
                            if (message != null) {
                                sms?.onOtpSuccess(message)
                            }

                        }
                        CommonStatusCodes.TIMEOUT -> {
                            // Handle timeout
                        }
                    }
                }
            }
    }

     interface  OtpReceiverListener{
        fun onOtpSuccess(intent:Intent);
        fun onOtpTimeOut()
    }
}