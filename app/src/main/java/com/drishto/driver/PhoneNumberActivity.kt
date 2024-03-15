package com.drishto.driver

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.drishto.driver.auth.AuthManager
import com.drishto.driver.database.TelemetryRepository
import com.drishto.driver.network.getUserId
import com.drishto.driver.network.saveUserId
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.StandardIntegrityManager
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import driver.LoginActivity
import driver.MainActivity
import driver.OTPActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class PhoneNumberActivity : ComponentActivity() {

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var telemetry : TelemetryRepository

    private lateinit var auth: FirebaseAuth

    private var number: String = ""

    private var otp: OtpVerification? = null

    val REQ_USER_CONSENT = 200


    var verification: String? = null
    var tokenAuth: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { res ->
            if (res.resultCode == Activity.RESULT_OK) {
                Log.i("TAG", "OnActivityResult")

                val googleCredential = oneTapClient.getSignInCredentialFromIntent(res.data)
                val idToken = googleCredential.googleIdToken
                when {
                    idToken != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    val user = auth.currentUser
                                    if (user != null) {
                                        if(getUserId(applicationContext) != user.uid){
                                            CoroutineScope(Dispatchers.IO).launch {
                                                telemetry.deleteAllTelemetry()
                                            }
                                        }
                                        Log.d("USER", "${user.uid} ")
                                        saveUserId(user.uid,applicationContext)
                                    }
                                    user?.getIdToken(true)?.addOnSuccessListener {
                                        it.token?.let { token -> updateUI(token) }
                                    }
                                    Log.d("TAG", "signInWithCredential:success")

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithCredential:failure", task.exception)
//                            updateUI(null)
                                }
                            }
                    }

                    else -> {
                        // Shouldn't happen.
                        Log.d("TAG", "No ID token!")
                    }
                }

            }
        }
    private fun handleError(exception: Exception) {
        Log.d("Handle", "handleError: $exception")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )

        val applicationContext: Context = applicationContext
        val cloudProjectNumber: Long = 9387484517

        val standardIntegrityManager = IntegrityManagerFactory.createStandard(applicationContext)
        var integrityTokenProvider: StandardIntegrityManager.StandardIntegrityTokenProvider? = null

        standardIntegrityManager.prepareIntegrityToken(
            StandardIntegrityManager.PrepareIntegrityTokenRequest.builder()
                .setCloudProjectNumber(cloudProjectNumber)
                .build()
        ).addOnSuccessListener { tokenProvider ->
            integrityTokenProvider = tokenProvider
            Log.d("Toekn", "onCreate: ${integrityTokenProvider}")
        }.addOnFailureListener { exception ->
            handleError(exception)
        }

        auth = Firebase.auth
        super.onCreate(savedInstanceState)

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
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
            val activity = LocalContext.current as? ComponentActivity
            if (activity != null) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            var text by remember { mutableStateOf(TextFieldValue("")) }
            var buttonText by remember { mutableStateOf("Send OTP") }
            var otpSeconds by remember { mutableStateOf(30) }
            var isButtonEnabled by remember { mutableStateOf(true) }
            val gradient = Brush.linearGradient(
                listOf(
                    Color(android.graphics.Color.parseColor("#FFFFFF")),
                    Color(android.graphics.Color.parseColor("#E8F1F8"))
                ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
            )


            LaunchedEffect(otpSeconds) {
                while (otpSeconds > 0) {
                    delay(1000)
                    otpSeconds--
                }

                if (isButtonEnabled) {
                    delay(10000)
                    isButtonEnabled = true
                    buttonText = "Send OTP"
                }
            }
            val primary = Color(0xFF92A3FD)
            val secondary = Color(0XFF9DCEFF)

            val context = LocalContext.current
            val app_name: String =getString(R.string.app_name).toUpperCase()

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = gradient)
                    .padding(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = gradient), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))

                    Spacer(modifier = Modifier.padding(16.dp))

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

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

                    OutlinedTextField(
                        value = text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(shape = RoundedCornerShape(4.dp))
                            .background(Color.White)
                            .align(Alignment.CenterHorizontally),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                        onValueChange = { newValue ->
                            if (newValue.text.length <= 10) {
                                text = newValue
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = primary,
                            focusedBorderColor = primary,
                            focusedLabelColor = primary,
                            focusedContainerColor = Color.Transparent,
                            unfocusedBorderColor = Color.White,

                        ),
                        maxLines = 1,
                        leadingIcon = {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "+91",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.padding(30.dp))


                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(48.dp),
                        enabled = if(buttonText=="Send OTP"){true}else{false},
                        onClick = {
                            if (text.text.length == 10 && isButtonEnabled && buttonText != "Sending...") {
                                number = "+91" + text.text.trim().toString()
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
                                isButtonEnabled = false
                                buttonText = "Sending..."


                                lifecycleScope.launch {
                                    delay(10000)
                                    isButtonEnabled = true
                                    buttonText = "Send OTP"
                                }
                            }
                        },
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
                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = buttonText,
                                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                    Row(modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                        val agreementText = "By continuing you agree that you have read and accepted"

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(android.graphics.Color.parseColor("#838383")),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.W300,
                                ),
                            ) {
                                append(agreementText)
                            }

                        }
                            ClickableText(
                                text = annotatedString,
                                onClick = { offset ->
//                                    clickHandler(offset)
                                }
                            )
                    }

                    Row(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center) {
                        val agreementText = "our"
                        val privacyPolicyText = "Privacy Policy"
                        val termsOfUseText = "Terms of Use"

                        val termsUrl = "https://samrish.com/policies/PrivacyPolicy.html"
                        val privacyUrl = "https://samrish.com/policies/PrivacyPolicy.html"

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(android.graphics.Color.parseColor("#838383")),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.W300,
                                ),
                            ) {
                                append(agreementText)
                            }

                            val startTerms = length
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Blue,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.W300
                                )
                            ) {
                                append(" $termsOfUseText")
                            }

                            addStringAnnotation("URL", termsUrl, startTerms, length)

                            val startPrivacy = length

                            withStyle(
                                style = SpanStyle(
                                    color = Color(android.graphics.Color.parseColor("#838383")),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.W300
                                )
                            ) {
                                append(" and ")
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = Color.Blue,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.W300
                                )
                            ) {
                                append("$privacyPolicyText")
                            }
                            addStringAnnotation("URL", privacyUrl, startPrivacy, length)
                        }

                        val clickHandler: (offset: Int) -> Unit = { offset ->
                            val annotations = annotatedString.getStringAnnotations("URL", offset, offset)
                            if (annotations.isNotEmpty()) {
                                val url = annotations.first().item
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            }
                        }

                        ClickableText(
                            text = annotatedString,
                            onClick = { offset ->
                                clickHandler(offset)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun startLoginActivity() {
        Log.d("TAG", "phoneLogin: ")
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }

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

    private fun updateUI(firebaseIdToken: String) {
        Log.d("TAG", "Going to Authenticate")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Login", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            Log.d("LIFE", "FCM Token: $token")


            authManager.authenticate(applicationContext, firebaseIdToken, task.result, {
                val myIntent = Intent(this, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent)
                finish()
            }, { errorMsg ->
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG).show()
            });

        })

    }
 }