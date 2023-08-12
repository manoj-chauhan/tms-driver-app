package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.samrish.driver.models.Profile
import com.samrish.driver.services.clearSession
import com.samrish.driver.services.fetchDriverProfile

@Composable
fun ProfileScreen(
    navController: NavHostController
) {

    val context = LocalContext.current;

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

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
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
                            Log.i("Logout", "Logout Successful")
                            clearSession(context)
                            navController.navigate(
                                "login",
                                NavOptions.Builder().setPopUpTo("home", true).build()
                            )
                        }
                    ) {
                        Text(text = "Logout")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview(
) {
//    ProfileScreen()
}
