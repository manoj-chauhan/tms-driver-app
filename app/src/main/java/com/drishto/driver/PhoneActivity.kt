package driver

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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.OtpVerification
import com.drishto.driver.R
import com.drishto.driver.auth.AuthManager
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
            val app_name: String =getString(R.string.app_name).toUpperCase()

            val lastTwoDigit =  phoneNumber.substring(phoneNumber.length - 2)

            val focusRequesters = remember { Array(6) { FocusRequester() } }
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            var countdownSeconds by remember { mutableStateOf(30) }
            var isResendEnabled by remember { mutableStateOf(false) }


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
                    .background(Color.White)
                    .padding(28.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(140.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "DRISHTO",
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 60.sp,
                            color = Color.Red
                        )
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (i in 0 until 6) {
                            OutlinedTextField(
                                value = if (i < text.text.length) TextFieldValue(text.text[i].toString()) else TextFieldValue(""),
                                onValueChange = {
                                    val newText = buildString {
                                        append(text.text)
                                        if (it.text.length == 1) {
                                            append(it.text[0])
                                        }
                                    }
                                    if (i < 5 && it.text.isNotEmpty()) {
                                        focusManager.moveFocus(FocusDirection.Next)
                                    }
                                    text = TextFieldValue(newText)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                singleLine = true,
                                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF92A3FD),
                                    focusedLabelColor = Color(0xFF92A3FD),
                                    cursorColor = Color(0xFF92A3FD)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    if (isResendEnabled) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp, bottom = 25.dp, start = 25.dp)
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
                                Text(text = "Wait for $countdownSeconds")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(15.dp))



                    val primary = Color(0xFF92A3FD)
                    val secondary = Color(0XFF9DCEFF)
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(48.dp),
                        onClick = {
                            val credential = PhoneAuthProvider.getCredential(
                                OTP, text.text.trim()
                            )
                            Log.d("TAG", "onCreate: $credential")
                            signInWithPhoneAuthCredential(credential) },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
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
                                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                val myIntent = Intent(this,MainActivity::class.java)
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(otp)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun hi() {
    val phoneNumber = "1234567890"
    val lastTwoDigit =  phoneNumber.substring(phoneNumber.length - 2)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {

        var text by remember {
            mutableStateOf("321456")
        }

        Column(
            modifier = Modifier
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(140.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "DRISHTO",
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 60.sp,
                    color = Color.Red
                )
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (i in 0 until 6) {
                    OutlinedTextField(
                        value = if (i < text.length) TextFieldValue(text[i].toString()) else TextFieldValue(""),
                        onValueChange = {
                            // Update the individual digit when its value changes
                            val newText = buildString {
                                append(text)
                                if (it.text.length == 1) {
                                    append(it.text[0])
                                }
                            }
                            text = TextFieldValue(newText).toString()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
//                            imeAction = if (i == 5) imeAction else ImeAction.Next
                        ),
                        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF92A3FD),
                            focusedLabelColor = Color(0xFF92A3FD),
                            cursorColor = Color(0xFF92A3FD)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(30.dp))


            val primary = Color(0xFF92A3FD)
            val secondary = Color(0XFF9DCEFF)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                onClick = {},
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
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
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun hiPreview() {
    hi()
}