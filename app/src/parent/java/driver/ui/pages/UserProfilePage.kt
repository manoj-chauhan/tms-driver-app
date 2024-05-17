package driver.ui.pages

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drishto.driver.PhoneNumberActivity
import com.drishto.driver.R
import com.drishto.driver.network.clearSession
import driver.headingColor
import driver.models.AccountProfile
import driver.profileLightGray
import driver.ui.viewmodels.AccountsProfileViewModel

@Composable
fun SettingsPage(onProfileSelected: () -> Unit) {
    val ap: AccountsProfileViewModel = hiltViewModel()
    ap.getProfileList()
    val profilesList by ap.profileList.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp)) {
        if(profilesList!= null) {
            UserProfilePage(
                profilesList = profilesList ?: listOf(), onProfileSelected
            )
        }
    }
}

@Composable
fun UserProfilePage(profilesList: List<AccountProfile>, onProfileSelected: () -> Unit) {
    val context = LocalContext.current
    var selectedProfileId by remember { mutableStateOf<String?>(null) }
    val fontFamily = FontFamily.SansSerif
    val name= profilesList.firstOrNull()?.name
    val parentProfiles = profilesList.filter { it.type == "Parent" }
    val firstParentName = parentProfiles.firstOrNull()?.name ?: "$name"


    Column(modifier = Modifier.padding(6.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boy),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = firstParentName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = headingColor,
                    fontFamily=fontFamily
                )

            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(profileLightGray)
                .padding(8.dp)
        ) {
            Column(modifier=Modifier.padding(10.dp)) {
                Text(
                    text = "Profiles",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                profilesList.forEach { profile ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clickable {
                                selectedProfileId = profile.id
                                onProfileSelected()

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(0.7f)
                            ,verticalAlignment = Alignment.CenterVertically)
                        {


                            Box(
                                modifier = Modifier
                                    .background(Color.White, shape = CircleShape)
                                    .size(30.dp)

                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.boy),
                                    contentDescription = "",
                                    modifier = Modifier.clip(CircleShape),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = profile.name,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),


                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color.LightGray, shape = CircleShape
                                )
                                .size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PersonAdd,
                                contentDescription = "Add Account",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Create Profile",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(13.dp))
        Column(modifier = Modifier.fillMaxWidth()) {

            Column {
                TextButton(onClick = {  }) {
                    Text(
                        "Terms of Use",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                TextButton(onClick = {  }) {
                    Text(
                        "Privacy Policy",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))

                TextButton(onClick = {
                    val myIntent =
                    Intent(context, PhoneNumberActivity::class.java)
                    clearSession(context)
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(myIntent)
                }) {
                    Text(
                        "Log Out",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}