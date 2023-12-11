package com.samrish.driver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.samrish.driver.auth.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class OTPActivity() : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var OTP: String
    private lateinit var otpNumber: String
    private lateinit var resentToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    @Inject
    lateinit var authManager: AuthManager;

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        OTP = intent.getStringExtra("OTP").toString()
        otpNumber = intent.getStringExtra("otp_number").toString()
        resentToken = intent.getParcelableExtra("resentToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        setContent {
            var text by remember { mutableStateOf(TextFieldValue("")) }
            var isResendButtonEnabled by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {
                LaunchedEffect(true) {
                    delay(TimeUnit.MINUTES.toMillis(1))
                    isResendButtonEnabled = true
                }

                LaunchedEffect(Unit) {
                    Log.d("OTP VALUE", "onCreate: $otpNumber")
                    text = TextFieldValue(otpNumber)
                }

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

                                    Spacer(modifier = Modifier.width(16.dp))
                                    if(isResendButtonEnabled) {
                                        Button(onClick = {
                                            resendOTPCredential()
                                        }) {
                                            Text(text = "Resend otp")
                                        }
                                    }
                                }
                            }
                        }

                    }

                }
            }


        }


    }

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
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: $e")

            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
                Log.d("TAG", "onVerificationFailed: $e")

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
}