package com.drishto.driver

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.MainActivity
import com.drishto.driver.OtpVerification
import com.drishto.driver.R
import com.drishto.driver.auth.AuthManager
import com.drishto.driver.database.TelemetryRepository
import com.drishto.driver.network.getUserId
import com.drishto.driver.network.saveUserId
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class OTPActivity() : ComponentActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var OTP: String
    private lateinit var otpNumber: String
    private lateinit var resentToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    private var otp: OtpVerification? = null

    private var text by mutableStateOf("")
    val REQ_USER_CONSENT = 200


    @Inject
    lateinit var authManager: AuthManager;

    @Inject
    lateinit var telemetry: TelemetryRepository;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        OTP = intent.getStringExtra("OTP").toString()
        otpNumber = intent.getStringExtra("otp_number").toString()
        resentToken = intent.getParcelableExtra("resentToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        setContent {
            val activity = LocalContext.current as? ComponentActivity
            if (activity != null) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            val context = LocalContext.current
            val app_name: String = getString(R.string.app_name).toUpperCase()

            val lastTwoDigit = phoneNumber.substring(phoneNumber.length - 2)
            var countdownSeconds by remember { mutableStateOf(30) }
            var isResendEnabled by remember { mutableStateOf(false) }
            val gradient = Brush.linearGradient(
                listOf(
                    Color(android.graphics.Color.parseColor("#FFFFFF")),
                    Color(android.graphics.Color.parseColor("#E8F1F8"))
                ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
            )



            LaunchedEffect(countdownSeconds) {
                while (countdownSeconds > 0) {
                    delay(1000)
                    countdownSeconds--
                }

                isResendEnabled = true
            }

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = gradient)
                    .padding(28.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = gradient),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))

                    Spacer(modifier = Modifier.padding(16.dp))
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                modifier = Modifier,
                                text = "DRISHTO",
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 48.sp,
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(
                                modifier = Modifier,
                                text = "An effort to make travel safer...",
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W300,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(50.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "OTP sent successfully to +91XXXXXXXX${lastTwoDigit}",
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                        )

                    }

                    BasicTextField(
                        value = text,
                        onValueChange = {
                            if (it.length <= 6) {
                                text = it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            repeat(6) { index ->
                                val number = when {
                                    index >= text.length -> ""
                                    else -> text[index]
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = number.toString(),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(2.dp)
                                            .background(
                                                Color.Black
                                            )
                                    )
                                }
                            }

                        }
                    }

                    Spacer(modifier = Modifier.padding(3.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp, bottom = 25.dp, start = 25.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
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
                                resendOTPCredential()
                                isResendEnabled = false
                                countdownSeconds = 30
                            },

                            )
                        Spacer(modifier = Modifier.padding(5.dp))
                        if (countdownSeconds > 0) {
                            Text(text = "$countdownSeconds")
                        }
                    }

                    Spacer(modifier = Modifier.padding(10.dp))


                    val primary = Color(0xFF92A3FD)
                    val secondary = Color(0XFF9DCEFF)
                    Button(
                        enabled = if (text.length == 6) {
                            true
                        } else {
                            false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(48.dp),
                        onClick = {
                            Log.d("OTP", "$text")
                            val credential = PhoneAuthProvider.getCredential(
                                OTP, text
                            )
                            Log.d("TAG", "onCreate: $credential")
                            signInWithPhoneAuthCredential(credential)
                        },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = if (text.length == 6) {
                                Color.White
                            } else {
                                Color.Gray
                            }
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(48.dp)
                                .background(
                                    brush = Brush.horizontalGradient(listOf(primary, secondary)),
                                    shape = RoundedCornerShape(50.dp)
                                ), contentAlignment = Alignment.Center
                        ) {
                            Row(modifier = Modifier) {
                                Icon(
                                    imageVector = Icons.Default.Login,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(start = 16.dp)
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = "Verify OTP",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
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
                Toast.makeText(this@OTPActivity, e.message, Toast.LENGTH_SHORT).show()

            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: $e")
                Toast.makeText(this@OTPActivity, e.message, Toast.LENGTH_SHORT).show()


            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
                Log.d("TAG", "onVerificationFailed: $e")
                Toast.makeText(this@OTPActivity, e.message, Toast.LENGTH_SHORT).show()


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
                        if (user != null) {
                            if (getUserId(applicationContext) != user.uid) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    telemetry.deleteAllTelemetry()
                                }
                            }
                            Log.d("USER", "${user.uid} ")
                            saveUserId(user.uid, applicationContext)
                        }
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
                    Toast.makeText(
                        this,
                        "The entered code is invalid. Check the code and try again",
                        Toast.LENGTH_LONG
                    ).show()
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
            Log.d("TAG", "updateUI: ${task.result}  ")
            authManager.authenticate(applicationContext, firebaseIdToken, task.result, {
                val myIntent = Intent(this, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
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
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}