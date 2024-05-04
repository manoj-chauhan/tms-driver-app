package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drishto.driver.R
import driver.models.AccountProfile
import driver.ui.viewmodels.AccountsProfileViewModel

@Composable
fun AccountsProfile(onProfileSelected: (profile: AccountProfile) -> Unit) {

    val ap:AccountsProfileViewModel = hiltViewModel()
    ap.getProfileList()

    val profilesList by ap.profileList.collectAsStateWithLifecycle()
    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)){

            Column(modifier = Modifier.fillMaxWidth()) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)) {

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)) {

                            Text(text = "Accounts", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))


                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, shape = CircleShape
                                    )
                                    .padding(top = 4.dp)
                                    .size(38.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.google),
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.Center)
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(text = "Manoj Chauhan", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))

                                }
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(text = "manojchauhan142@gmail.com", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))

                                }
                            }
                            
                        }

                    }

                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        Text(text = "PROFILES", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(20.dp))

                        profilesList.let {
                            it?.take(it.size)?.forEach { profile ->
                                Profiles(profile, onProfileSelected)
                            }
                        }
                    }

                }

            }
        }

    }
}

@Composable
fun Profiles(profile: AccountProfile, onClick: (tripsToDriver: AccountProfile) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().clickable { onClick(profile) }) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(
                        Color.White, shape = CircleShape
                    )
                    .padding(top = 4.dp)
                    .size(45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = profile.name,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                    )

                }


                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = profile.anchor,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Gray
                        )
                    )

                }
            }


        }
    }
        Spacer(modifier = Modifier.height(20.dp))
}