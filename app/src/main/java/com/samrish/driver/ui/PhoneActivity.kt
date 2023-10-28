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
import androidx.compose.material.icons.filled.PhoneEnabled
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.samrish.driver.network.authenticate

class OTPActivity(): ComponentActivity() {

    val auth = FirebaseAuth.getInstance()
    private  lateinit var OTP: String
    private lateinit var resentToken:PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        OTP = intent.getStringExtra("OTP").toString()
        resentToken = intent.getParcelableExtra("resentToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

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
                                            imageVector = Icons.Default.PhoneEnabled,
                                            contentDescription = "emailIcon"
                                        )
                                    }   ,
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
                                        val credential = PhoneAuthProvider.getCredential(OTP,
                                            text.toString()
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
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                Log.d("TAG", "signInWithPhoneAuthCredential: ")
//                    val user = auth.currentUser
//                if (task.isSuccessful) {
//                    when {
//                        resentToken != null -> {
//                            user?.getIdToken(true)?.addOnSuccessListener {
//                                it.token?.let { token -> updateUI(token) }
//                            }
//                        }
//                    }
//                }
//            }
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

    private fun updateUI(firebaseIdToken:String) {
        Log.d("TAG", "updateUI: ")
        authenticate(this, firebaseIdToken, {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            finish()
        }, {

        })
    }

}