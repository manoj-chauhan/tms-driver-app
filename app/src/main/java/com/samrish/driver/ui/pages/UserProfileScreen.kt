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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.samrish.driver.ui.viewmodels.UserProfileViewModel

@Composable
fun UserProfile(vm: UserProfileViewModel = viewModel()) {
    val context = LocalContext.current

    val isDetailsSelected = remember { mutableStateOf(false); }
    val isDialogVisible = remember { mutableStateOf(false); }

    val userDetail by vm.userDetail.collectAsStateWithLifecycle()
    vm.userDetail(context = context)

    val filteredCompanies = userDetail?.companiesList?.filter { company ->
        "DRIVER" in company.roles
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
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
                        horizontalArrangement = Arrangement.End
                    ) {

                        Button(onClick = {isDetailsSelected.value  =  true }) {
                            Text(text = "Add Details")
                        }
                    }
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
                            modifier = Modifier.size(25.dp) // Adjust the size as needed
                        )
                    }
                    Row {
                        Text(
                            text = "(${userDetail?.userName})", style = TextStyle(
                                color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Row{
                        Text(text = "Companies List", style = TextStyle(
                            color = Color.Black, fontSize = 19.sp, fontWeight = FontWeight.Bold
                        ))
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                        filteredCompanies?.forEach { company ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = company.companyCode, style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = company.companyName, style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Age", style = TextStyle(
                                color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(20.dp) // Adjust the size as needed
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
                                text = "Address ",
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
                            modifier = Modifier.size(20.dp) // Adjust the size as needed
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
                                modifier = Modifier.size(25.dp) // Adjust the size as needed
                            )

                        }

                    }

                }
            }
        }
    }

    if(isDetailsSelected.value){
        AddUserDetail(context,
            setShowDialog = {
                isDetailsSelected.value = it
        })
    }
}

@Composable
@Preview
fun UserProfilePreview() {
    UserProfile()
}