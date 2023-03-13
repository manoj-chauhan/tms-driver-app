package com.samrish.driver.ui.composition

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavHostController
) {
    Column() {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(200.dp)
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
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Column() {
                    TextField(
                        label = { Text(text = "Username") },
                        value = username, onValueChange = { username = it }
                    )
                    TextField(
                        label = { Text(text = "Password") },
                        value = password, onValueChange = { password = it }
                    )
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            navController.navigate(
                                "home",
                                NavOptions.Builder().setLaunchSingleTop(true).build()
                            )
                        }
                    ) {
                        Text(text = "Click Me")
                    }
                }
            }
        }
    }
}