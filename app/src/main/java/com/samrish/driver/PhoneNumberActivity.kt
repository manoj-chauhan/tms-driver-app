package com.samrish.driver

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)

class PhoneNumberActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    private var number: String = ""

    private var otp: OtpVerification? = null

    val REQ_USER_CONSENT = 200


    var verification: String? = null
    var tokenAuth: PhoneAuthProvider.ForceResendingToken? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {


        auth = Firebase.auth
        super.onCreate(savedInstanceState)

//        autoOtpReceiver()


        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                val code: String = credential.smsCode!!
                Log.d("TD", "onVerificationCompleted: $code ")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Log.d("TAG", "onVerificationFailed: ${e.message} ${e.localizedMessage}")
                    Toast.makeText(this@PhoneNumberActivity, e.message, Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.d("TAG", "onVerificationFailed: ${e.message}")
                    Toast.makeText(this@PhoneNumberActivity, e.message, Toast.LENGTH_SHORT).show()

                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                super.onCodeSent(verificationId, token)
                verification = verificationId
                tokenAuth = token
                val intent = Intent(this@PhoneNumberActivity, OTPActivity::class.java)
                intent.putExtra("OTP", verificationId)
                intent.putExtra("resentToken", token)
                intent.putExtra("phoneNumber", number)
                Log.d("TAG", "onCodeSent: $verificationId and $token and, $otp")
                startActivity(intent)
            }
        }

        setContent {
            var text by remember { mutableStateOf(TextFieldValue("")) }
            var isPhoneNumberValid by remember { mutableStateOf(false) }
            var otpSeconds by remember { mutableStateOf(30) }
            var isButtonEnabled by remember { mutableStateOf(true) }


            LaunchedEffect(otpSeconds) {
                while (otpSeconds > 0) {
                    delay(1000)
                    otpSeconds--
                }

                isButtonEnabled = true
            }


            val context = LocalContext.current


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(
                                PaddingValues(

                                    top = 30.dp,
                                    end = 12.dp,
                                    bottom = 20.dp
                                )
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text(
                                text = "DRISHTO", style = TextStyle(
                                    color = Color.Red,
                                    fontSize = 40.sp, fontWeight = FontWeight.ExtraBold
                                )
                            )

                        }


                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        shape = RoundedCornerShape(35.dp, 35.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                                .padding(
                                    start = 12.dp, top = 50.dp,
                                    end = 12.dp,
                                    bottom = 20.dp
                                )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                TextField(
                                    value = text,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                                    leadingIcon = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Text(
                                                text = "+91",
                                                modifier = Modifier.padding(start = 16.dp),
                                                style = TextStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp
                                                )
                                            )
                                        }
                                    },
                                    label = { Text(text = "Enter Your Phone Number") },
                                    onValueChange = { newTextFieldValue ->
                                        if (newTextFieldValue.text.length <= 10) {
                                            text = newTextFieldValue
                                            isPhoneNumberValid =
                                                newTextFieldValue.text.length == 10 && Patterns.PHONE.matcher(
                                                    newTextFieldValue.text
                                                ).matches()
                                        } else {
                                            Toast.makeText(
                                                this@PhoneNumberActivity,
                                                "Please enter a correct number",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                )



                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        onClick = {
                                            if (isButtonEnabled) {
                                                isButtonEnabled = false
                                                number = "+91" + text.text.trim().toString()
                                                Log.d("TAG", "onCreate: $number")
                                                otpSeconds = 30
                                                val options = PhoneAuthOptions.newBuilder(auth)
                                                    .setPhoneNumber(number) // Phone number to verify
                                                    .setTimeout(
                                                        0L,
                                                        TimeUnit.SECONDS
                                                    ) // Timeout and unit
                                                    .setActivity(this@PhoneNumberActivity)
                                                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                                                    .build()
                                                PhoneAuthProvider.verifyPhoneNumber(options)

                                            } else {
                                                Toast.makeText(
                                                    this@PhoneNumberActivity,
                                                    "Please wait for 30 seconds before trying again",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        },
                                        enabled = isPhoneNumberValid && isButtonEnabled,
                                    ) {
                                        Text(text = "Send OTP")
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }


//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun autoOtpReceiver() {
//        Log.d("auto", "autoOtpReceiver: ")
//        val client = SmsRetriever.getClient(this)
//        client.startSmsUserConsent(null)
//    }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun registerBroadCastReceiver() {
//        Log.d("register", "registerBroadCastReceiver: ")
//        otp = OtpVerification()
//        otp!!.sms = object : OtpVerification.OtpReceiverListener {
//            override fun onOtpSuccess(intent: Intent) {
//                startActivityForResult(intent, REQ_USER_CONSENT)
//            }
//
//            override fun onOtpTimeOut() {
//                TODO("Not yet implemented")
//            }
//
//        }
//
//        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//        registerReceiver(otp, intentFilter, RECEIVER_NOT_EXPORTED)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQ_USER_CONSENT) {
//            if (resultCode == RESULT_OK && data != null) {
//                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
//                getOtpFromMessage(message)
//            }
//        }
//    }
//
//    private fun getOtpFromMessage(message: String?) {
//        if (message != null) {
//            val otpPattern = Pattern.compile("(|^)\\d{6}")
//            val matcher = otpPattern.matcher(message)
//            if (matcher.find()) {
//                val otpValue = matcher.group(0)
//                Log.d("getOTP", "getOtpFromMessage: $otpValue")
//
//                // Start OTPActivity with the OTP value
//                val intent = Intent(this@PhoneNumberActivity, OTPActivity::class.java)
//                intent.putExtra("OTP", verification)
//                intent.putExtra("resentToken", tokenAuth)
//                intent.putExtra("phoneNumber", number)
//                intent.putExtra("otp_number", otpValue)
//                startActivity(intent)
//            }
//        }
//    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = task.result?.user
                    Log.d("TAG", "signInWithCredential:success $user")
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(
                            this,
                            "The entered code is invalid. Check the code and try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    // Update UI
                }
            }
    }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    override fun onStart() {
//        super.onStart()
//        registerBroadCastReceiver()
//    }
}
