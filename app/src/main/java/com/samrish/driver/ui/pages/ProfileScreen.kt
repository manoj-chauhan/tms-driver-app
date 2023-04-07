package com.samrish.driver.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row() {
            Text(text = "Manoj Chauhan")
        }
        Row() {
            Text(text = "+91-9899547045 (primary)")
        }
        Row() {
            Text(text = "+91-9899453434 (secondary)")
        }
        Row() {
            Button(
                onClick = {

                }
            ) {
                Text(text = "Logout")
            }
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview(
) {
    ProfileScreen()
}
