package com.samrish.driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.R
import com.samrish.driver.viewmodels.UserProfileViewModel

@Composable
fun UserProfile(vm: UserProfileViewModel = viewModel()) {
    val context = LocalContext.current

    val userDetail by vm.userDetail.collectAsStateWithLifecycle()
    vm.userDetail(context = context)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = 20.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .zIndex(2f)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.signal),

                    contentDescription = null, // Provide a proper content description
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(2f)
                )


            }
            Card(
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxWidth()
                    .fillMaxSize(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(35.dp, 35.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 36.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${userDetail?.name}", style = TextStyle(

                                color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(30.dp) // Adjust the size as needed
                        )
                    }
                    Row {
                        Text(
                            text = "(${userDetail?.username})", style = TextStyle(
                                color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "21 years old", style = TextStyle(
                                color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(25.dp) // Adjust the size as needed
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier.width(300.dp)) {
                            Text(
                                text = "D-608 3rd Floor, Avantika, Sector-1, Rohini, Delhi- 110085 ",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(25.dp) // Adjust the size as needed
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "CHANGE PASSWORD", style = TextStyle(
                                color = Color.Magenta,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Driving License", style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                                contentDescription = "Edit Icon",
                                modifier = Modifier.size(30.dp) // Adjust the size as needed
                            )

                        }

                    }

                }
            }
        }
    }
}

@Composable
@Preview
fun UserProfilePreview() {
    UserProfile()
}