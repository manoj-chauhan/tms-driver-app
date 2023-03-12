package com.samrish.driver.ui.composition

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun Login(
    navController: NavHostController
) {
    Button(onClick = { navController.navigate("home") }) {
        Text(text = "Click Me")
    }
}