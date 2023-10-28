package com.samrish.driver.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)

class PhoneNumberActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    private var number: String=""

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth
        super.onCreate(savedInstanceState)

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
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                // Save verification ID and resending token so we can use them later
                super.onCodeSent(verificationId, token)
                val intent = Intent(this@PhoneNumberActivity, OTPActivity::class.java)
                intent.putExtra("OTP", verificationId)
                intent.putExtra("resentToken", token)
                intent.putExtra("phoneNumber", number)
                Log.d("TAG", "onCodeSent: $verificationId and $token")
                startActivity(intent)
            }
        }

        setContent {


            var text by remember { mutableStateOf(TextFieldValue("")) }

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
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OutlinedTextField(
                                    value = text,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Phone,
                                            contentDescription = "emailIcon"
                                        )
                                    },
                                    label = { Text(text = "Enter Your Phone Number") },
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
                                        number = "+91"+text.text.trim().toString()
                                        Log.d("TAG", "onCreate: $number")
                                        val options = PhoneAuthOptions.newBuilder(auth)
                                            .setPhoneNumber(number) // Phone number to verify
                                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                            .setActivity(this@PhoneNumberActivity)
                                            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                                            .build()
                                        PhoneAuthProvider.verifyPhoneNumber(options)
                                    }) {
                                        Text(text = "Get otp")
                                    }
                                }
                            }


                        }

                    }

                }
            }

        }
    }

//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
//                override fun onComplete(task: Task<AuthResult?>) {
//                    if (task.isSuccessful) {
//                        val i = Intent(this@PhoneNumberActivity, MainActivity::class.java)
//                        startActivity(i);
//                        finish()
//                    }
//                }
//            })
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
                    }
                    // Update UI
                }
            }
    }




    override fun onStart() {
        super.onStart()
        if(auth.currentUser!= null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
