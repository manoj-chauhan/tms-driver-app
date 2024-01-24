package com.drishto.driver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.drishto.driver.auth.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Login"

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    @Inject
    lateinit var authManager: AuthManager

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { res ->
            if (res.resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "OnActivityResult")

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
                                    user?.getIdToken(true)?.addOnSuccessListener {
                                        it.token?.let { token -> updateUI(token) }
                                    }
                                    Log.d(TAG, "signInWithCredential:success")

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                            updateUI(null)
                                }
                            }
                    }

                    else -> {
                        // Shouldn't happen.
                        Log.d(TAG, "No ID token!")
                    }
                }

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()

//        fun loginSuccess() {
//            val myIntent = Intent(this, MainActivity::class.java)
//            startActivity(myIntent)
//        }



        setContent {
            Column() {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(400.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "DRISHTO",
                        fontSize = 60.sp,
                        color = Color.Red
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    var username by remember {
                        mutableStateOf("")
                    }
                    var password by remember {
                        mutableStateOf("")
                    }

                    var context = LocalContext.current



                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(48.dp)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth(),
                                label = { Text(text = "Username") },
                                value = username, onValueChange = { username = it }
                            )
                            TextField(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth(),
                                label = { Text(text = "Password") },
                                value = password, onValueChange = { password = it }
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onClick = {
//                            attemptLogin(
//                                context,
//                                username,
//                                password,
//                                { onLoginSuccess() },
//                                { onLoginFailure() }
                                }
                            ) {
                                Text(text = "Login")
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.google),
                                    contentDescription = "Home Icon",
                                    modifier = Modifier.clickable {
                                        oneTapClient.beginSignIn(signInRequest)
                                        .addOnSuccessListener { result ->
                                            Log.d(TAG, "OnOneTapClient Success")
                                            val intentSenderRequest =
                                                IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                                            resultLauncher.launch(intentSenderRequest)
                                        }
                                        .addOnFailureListener { e ->
                                            // No saved credentials found. Launch the One Tap sign-up flow, or
                                            // do nothing and continue presenting the signed-out UI.
                                            Log.d(TAG, e.localizedMessage)
                                        }
                                    }
                                )

                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "Home Icon",
                                    modifier = Modifier.clickable {
                                        phoneLogin()
                                    }
                                )
                            }
                        }
                    }
                }
            }
            FirebaseApp.initializeApp(this);
        }
    }

    private fun updateUI(firebaseIdToken: String) {
        Log.d(TAG, "Going to Authenticate")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Login", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            authManager.authenticate(applicationContext, firebaseIdToken, task.result, {
                val myIntent = Intent(this, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent)
                finish()
            },{ errorMsg ->
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG).show()
            });

        })

    }

    private fun phoneLogin() {
        Log.d(TAG, "phoneLogin: ")
        val loginIntent = Intent(this, PhoneNumberActivity::class.java)
        startActivity(loginIntent)
    }


}
