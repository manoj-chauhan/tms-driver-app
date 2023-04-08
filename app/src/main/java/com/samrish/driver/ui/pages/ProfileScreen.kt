package com.samrish.driver.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Profile
import com.samrish.driver.services.fetchDriverProfile

@Composable
fun ProfileScreen() {

    val profileDetail = remember {
        mutableStateOf<Profile?>(null)
    }

    fetchDriverProfile(
        LocalContext.current,
        onProfileFetched = {
            profileDetail.value = it
        }
    )

    profileDetail.value?.let {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row() {
                Text(text = it.driverName)
            }
            Row() {
                Text(text = it.primaryContact)
            }
            Row() {
                Text(text = it.secondaryContact)
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
}


@Preview
@Composable
fun ProfileScreenPreview(
) {
    ProfileScreen()
}
