package com.samrish.driver.ui

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samrish.driver.R
import com.samrish.driver.services.authenticate

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val REQ_ONE_TAP = 140

    private val TAG = "Login"

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

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
                    .build())
            .build()

        setContent {
            Button(onClick = {
                oneTapClient.beginSignIn(signInRequest)
                    .addOnSuccessListener(this) { result ->
                        try {

                            startIntentSenderForResult(
                                result.pendingIntent.intentSender, REQ_ONE_TAP,
                                null, 0, 0, 0, null)
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                        }
                    }
                    .addOnFailureListener(this) { e ->
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, e.localizedMessage)
                    }
            }) {

                Text(text = "Click to Authenticate")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
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

    private fun updateUI(firebaseIdToken:String) {
        authenticate(this, firebaseIdToken, {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            finish()
        }, {

        })
    }

}