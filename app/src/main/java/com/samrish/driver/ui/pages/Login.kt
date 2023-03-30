package com.samrish.driver.ui.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import attemptLogin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavHostController
) {
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
                color = Color.White
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

            fun onLoginSuccess() {
                Log.i("Login", "Login Successful")
                navController.navigate(
                    "home",
                    NavOptions.Builder().setPopUpTo("login", true).build()
                )
            }

            fun onLoginFailure() {
                Log.i("Login", "Login Failure")
                Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
            }

            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier
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
                            attemptLogin(
                                context,
                                username,
                                password,
                                { onLoginSuccess() },
                                { onLoginFailure() }
                            )
                        }
                    ) {
                        Text(text = "Login")
                    }
                }
            }
        }
    }
}