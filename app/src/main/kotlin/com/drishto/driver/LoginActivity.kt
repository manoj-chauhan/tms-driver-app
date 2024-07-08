package driver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.MainActivity
import com.drishto.driver.R
import com.drishto.driver.auth.AuthManager
import com.drishto.driver.database.TelemetryRepository
import com.drishto.driver.network.getUserId
import com.drishto.driver.network.saveUserId
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Login"

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var telemetry: TelemetryRepository

    private lateinit var signInRequest: BeginSignInRequest



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

//        fun loginSuccess() {
//            val myIntent = Intent(this, MainActivity::class.kotlin)
//            startActivity(myIntent)
//        }
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

        val app_name: String = getString(R.string.app_name).toUpperCase()
        setContent {
        val context = LocalContext.current
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(28.dp)
            ) {

                var username by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var passwordVisible by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        text = "Welcome To", style = TextStyle(
                            color = Color.Black,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

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

                    OutlinedTextField(label = { Text("Email Address") },
                        value = username,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Color(0xFFF7F8F8)),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true,
                        maxLines = 1,
                        onValueChange = { username = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF92A3FD),
                            focusedLabelColor = Color(0xFF92A3FD),
                            cursorColor = Color(0xFF92A3FD)
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "emailIcon"
                            )
                        }
                    )

                    Spacer(modifier = Modifier.padding(12.dp))

                    OutlinedTextField(
                        label = { Text("Password") },
                        value = password,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Color(0xFFF7F8F8)),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = { password = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF92A3FD),
                            focusedLabelColor = Color(0xFF92A3FD),
                            cursorColor = Color(0xFF92A3FD)
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "emailIcon"
                            )
                        },
                        trailingIcon = {
                            val iconImage = if (passwordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }
                            val description = if (passwordVisible) {
                                stringResource(id = R.string.hide_password)
                            } else {
                                "Show Password"
                            }

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = iconImage, contentDescription = description)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.padding(40.dp))


                    val primary = Color(0xFF92A3FD)
                    val secondary = Color(0XFF9DCEFF)
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(48.dp),
                        onClick = {
                            attemptLogin(
                                context,
                                username,
                                password,
                            ) },
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
                                    text = "Login",
                                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                )

                            }
                        }

                    }

                    Spacer(modifier = Modifier.padding(20.dp))

                }
            }
            FirebaseApp.initializeApp(this);
        }
    }

    private fun attemptLogin(context: Context, username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
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
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
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
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(myIntent)
                finish()
            }, { errorMsg ->
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG).show()
            });

        })

    }

}