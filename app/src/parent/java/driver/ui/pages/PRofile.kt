package driver.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.drishto.driver.R
import com.drishto.driver.ui.viewmodels.UserProfileViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import driver.models.EducationList
import driver.ui.viewmodels.EducationListViewModel


@Composable
fun profile(navController: NavController) {
    val customLightGray = Color(android.graphics.Color.parseColor("#F0F0F0"))
    val activecolor = Color(android.graphics.Color.parseColor("#6200EE"))
    var selectedProfileId by remember { mutableStateOf<String?>(null) }

    val fontFamily = FontFamily.SansSerif
    val colortext = Color(android.graphics.Color.parseColor("#1c1b1f"))

    val onBackPressed: () -> Unit = {
        navController.navigateUp()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())

        ) {
            Box(modifier =Modifier.background(Color.Transparent)) {







                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.boy),
                            contentDescription = "student's photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
//            modifier = Modifier.zIndex(1f)
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                    ) {
                        IconButton(
                            onClick = onBackPressed,
//                    modifier = Modifier
//                        .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                    ) {
                        IconButton(
                            onClick = { },
//                    modifier = Modifier
//                        .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menu"
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier.height(20.dp))


            // About Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp)
            ) {
                Text(
                    text = "Krish Chauhan",
                    fontSize = 24.sp,

                    fontFamily = fontFamily,
                    color = colortext
                )
                Text(
                    text = "@kris",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = fontFamily,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Student at MAAPS",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = fontFamily,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(horizontal = 25.dp)
            ) {


                // Experience Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "EXPERIENCE",
                        fontSize = 14.sp,
                        fontFamily = fontFamily,

                        color = colortext,

                        )

                    Spacer(modifier = Modifier.height(10.dp))


                    Column() {
                        Row(
//                            verticalAlignment = Alignment.Top
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dps),
                                contentDescription = "dps",

                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Delhi Public School",
                                    fontSize = 14.sp,
                                    fontFamily = fontFamily,
                                    color = colortext,


                                    )
                                Text(
                                    text = "PGT",

                                    fontFamily = fontFamily,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
                                )

                            }
                        }
                        Text(
                            text = "Taught science and mathematics to secondary level",
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            color = Color.Gray,

                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .padding(top = 2.dp)
                ) {

                    Column() {
                        Row(
//                            verticalAlignment = Alignment.Top
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dps),
                                contentDescription = "dps",

                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Delhi Public School",
                                    fontFamily = fontFamily,
                                    color = colortext,

                                    )
                                Text(
                                    text = "PGT",

                                    fontFamily = fontFamily,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
                                )

                            }
                        }
                        Text(
                            text = "Taught science and mathematics to secondary level",
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            color = Color.Gray,

                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Education Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "EDUCATION",
                        fontFamily = fontFamily,
                        fontSize = 14.sp,

                        color = colortext
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Column {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dps),
                                contentDescription = "dps",
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Delhi Public School",
                                    fontFamily = fontFamily,

                                    )
                                Text(
                                    text = "12th Standard (2023-2024)",
                                    fontFamily = fontFamily,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Extra Curricular Activities Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "EXTRA CURRICULAR ACTIVITIES",
                        fontSize = 14.sp,
                        fontFamily = fontFamily,

                        color = colortext
                    )
                    Spacer(modifier = Modifier.height(10.dp))


                    Column {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dps),
                                contentDescription = "dps",
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Football Club",
                                    fontFamily = fontFamily,
                                    color = colortext
                                )
                                Text(
                                    text = "Member (2023-2024)",
                                    fontFamily = fontFamily,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Achievements Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "ACHIEVEMENTS",
                        fontSize = 14.sp,
                        fontFamily = fontFamily,

                        color = colortext,
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    Column {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dps),
                                contentDescription = "dps",
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Inter School Robotic Championship",
                                    fontFamily = fontFamily,

                                    )
                                Text(
                                    text = "Secured 1st Position",
                                    fontFamily = fontFamily,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }


        }
    }
}


//@Composable
////fun educationList(educationList:List<EducationList>){
////    educationList?.forEach() { information ->
//fun educationList() {
//
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 16.dp, top = 20.dp, bottom = 20.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .background(
//                    Color.White, shape = CircleShape
//                )
//                .size(60.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.dps),
//                contentDescription = "",
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(200.dp)
//                    .clip(CircleShape)
//                    .border(
//                        width = 0.dp, Color.White, shape = CircleShape
//                    ),
//                contentScale = ContentScale.FillBounds
//            )
//        }
//
//        Spacer(modifier = Modifier.width(14.dp))
//
//        Box(modifier = Modifier.fillMaxWidth()) {
//            Column {
//                Text(
//                    text = "{information.schoolName}",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = Color.Black
//                )
//
//                Text(
//                    text = "{information.schoolAddress}",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Light,
//                    color = Color.Black
//                )
//                Text(
//                    text = "{information.status}",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Light,
//                    color = Color.Black
//                )
//
//            }
//
//        }
//    }
//
//
//}


//@Composable
//@Preview
//fun proPreview() {
//    profile()
//}