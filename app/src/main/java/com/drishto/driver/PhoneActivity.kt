package com.drishto.driver

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneEnabled
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.drishto.driver.auth.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class OTPActivity() : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var OTP: String
    private lateinit var otpNumber: String
    private lateinit var resentToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    private var otp: OtpVerification? = null

    private var text by mutableStateOf(TextFieldValue(""))
    val REQ_USER_CONSENT = 200


    @Inject
    lateinit var authManager: AuthManager;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        OTP = intent.getStringExtra("OTP").toString()
        otpNumber = intent.getStringExtra("otp_number").toString()
        resentToken = intent.getParcelableExtra("resentToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!
        autoOtpReceiver()

        setContent {
            val context = LocalContext.current

            var countdownSeconds by remember { mutableStateOf(30) }
            var isResendEnabled by remember { mutableStateOf(false) }


            LaunchedEffect(countdownSeconds) {
                while (countdownSeconds > 0) {
                    delay(1000)
                    countdownSeconds--
                }

                isResendEnabled = true
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {

                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(
                                PaddingValues(
                                    top = 30.dp, end = 12.dp, bottom = 20.dp
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
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.ExtraBold
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
                                    start = 12.dp, top = 50.dp, end = 12.dp, bottom = 20.dp
                                )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OutlinedTextField(value = text,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.PhoneEnabled,
                                            contentDescription = "emailIcon"
                                        )
                                    },
                                    label = { Text(text = "Enter Your Otp Sent") },
                                    onValueChange = {
                                        text = it
                                    }
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 25.dp, bottom = 25.dp, start = 30.dp)
                                ) {
                                    Text(text = "Didn't received the OTP?")
                                    Spacer(modifier = Modifier.padding(10.dp))
                                    ClickableText(
                                        text = buildAnnotatedString {
                                            withStyle(style = SpanStyle(color = if (isResendEnabled) Color.Blue else Color.Gray)) {
                                                append("Resend OTP")

                                            }
                                        },
                                        onClick = { offset ->
                                            if (isResendEnabled) {
                                                // Handle click only if resend is enabled
                                                resendOTPCredential()
                                                isResendEnabled = false
                                                countdownSeconds = 30
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Please wait for the timer to finish",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        },
                                        modifier = Modifier.clickable {
                                            // Handle click on the entire ClickableText
                                            if (!isResendEnabled) {
                                                Toast.makeText(
                                                    context,
                                                    "Please wait for the timer to finish",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                    )
                                    Spacer(modifier = Modifier.padding(5.dp))
                                    if(countdownSeconds> 0 ) {
                                        Text(text = "Wait for $countdownSeconds")
                                    }
                                }


                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(onClick = {
                                        val credential = PhoneAuthProvider.getCredential(
                                            OTP, text.text.trim()
                                        )
                                        Log.d("TAG", "onCreate: $credential")
                                        signInWithPhoneAuthCredential(credential)

                                    }) {
                                        Text(text = "Verify otp")
                                    }
                                }
                            }
                        }

                    }

                }
            }


        }


    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun resendOTPCredential() {

        phoneNumber = intent.getStringExtra("phoneNumber")!!
        resentToken = intent.getParcelableExtra("resentToken")!!


        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(0L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resentToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        autoOtpReceiver()
    }

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
                Log.d("TAG", "onVerificationFailed: $e")
                Toast.makeText(this@OTPActivity,e.message ,Toast.LENGTH_SHORT).show()

            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: $e")
                Toast.makeText(this@OTPActivity,e.message ,Toast.LENGTH_SHORT).show()


            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
                Log.d("TAG", "onVerificationFailed: $e")
                Toast.makeText(this@OTPActivity,e.message ,Toast.LENGTH_SHORT).show()


            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            super.onCodeSent(verificationId, token)
            OTP = verificationId
            resentToken = token
        }
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    Log.d("TAG", "signInWithCredential:success $user")
                    if (user != null) {
                        user.getIdToken(true).addOnSuccessListener { tokenResult ->
                                val idToken = tokenResult.token
                                if (idToken != null) {
                                    updateUI(idToken)
                                }
                                Log.d("TAG", "ID Token: $idToken")
                            }.addOnFailureListener { exception ->
                                // Handle the case where getting the ID token failed
                                Log.e("TAG", "Error getting ID token: ${exception.message}")
                            }
                    }

                    Log.d("TAG", "signInWithCredential:success $user")

                } else {
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "The entered code is invalid. Check the code and try again", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun updateUI(firebaseIdToken: String) {
        Log.d("TAG", "Going to Authenticate $firebaseIdToken")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Login", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            Log.d("TAG", "updateUI: Inside  ")
            authManager.authenticate(applicationContext, firebaseIdToken, task.result, {
                val myIntent = Intent(this, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent)
                finish()
            }, { errorMsg ->
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG).show()
            })
        })
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun registerBroadCastReceiver() {
        Log.d("register", "registerBroadCastReceiver: ")
        otp = OtpVerification()
        otp!!.sms = object : OtpVerification.OtpReceiverListener {
            override fun onOtpSuccess(intent: Intent) {
                startActivityForResult(intent, REQ_USER_CONSENT)
            }

            override fun onOtpTimeOut() {
                TODO("Not yet implemented")
            }

        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(otp, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun autoOtpReceiver() {
        Log.d("auto", "autoOtpReceiver: ")
        val client = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                getOtpFromMessage(message)
            }
        }
    }

    private fun getOtpFromMessage(message: String?) {
        if (message != null) {
            val otpPattern = Pattern.compile("(|^)\\d{6}")
            val matcher = otpPattern.matcher(message)
            if (matcher.find()) {
                val otpValue = matcher.group(0)
                Log.d("getOTP", "getOtpFromMessage: $otpValue")

                // Start OTPActivity with the OTP value
                runOnUiThread {
                    text = TextFieldValue(otpValue)
                }
            }
        }
    }
}