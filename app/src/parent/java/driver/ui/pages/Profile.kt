package driver.ui.pages

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drishto.driver.R


@Composable
fun Profile(navController: NavController) {
    val customLightGray = Color(android.graphics.Color.parseColor("#F0F0F0"))
    val activecolor = Color(android.graphics.Color.parseColor("#6200EE"))
    var selectedProfileId by remember { mutableStateOf<String?>(null) }

    val fontFamily = FontFamily.SansSerif
    val colortext = Color(android.graphics.Color.parseColor("#1c1b1f"))

    val onBackPressed: () -> Unit = {
        navController.navigateUp()
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())

    ) {

        PersonalDetail(onBackPressed = onBackPressed)
        Spacer(modifier = Modifier.height(20.dp))
        ExperienceDetail()
        EducationDetail()
        ExtraCurricularActivitiesDetail()
        AchievementsDetail();

    }


}

@Composable
fun PersonalDetail(
    onBackPressed: () -> Unit
) {
    Box(modifier = Modifier.background(Color.Transparent)) {
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
}


@Composable
fun ExperienceDetail() {
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
        Spacer(modifier = Modifier.height(6.dp))
        Experience();
        Spacer(modifier = Modifier.height(6.dp))
        Experience();

    }
}

@Composable
fun Experience() {
    Column() {
        Row() {
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

@Composable
fun EducationDetail() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "EDUCATION",
            fontSize = 14.sp,
            fontFamily = fontFamily,

            color = colortext,

            )
        Spacer(modifier = Modifier.height(6.dp))
        Education();
        Spacer(modifier = Modifier.height(6.dp))
        Education();

    }
}

@Composable
fun Education() {
    Row() {
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
                text = "Xth Standard(2023-2024)",

                fontFamily = fontFamily,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

        }
    }
}

@Composable
fun ExtraCurricularActivitiesDetail() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "EXTRA CURRICULAR ACTIVITIES",
            fontSize = 14.sp,
            fontFamily = fontFamily,

            color = colortext,

            )
        Spacer(modifier = Modifier.height(6.dp))
        ExtraCurricularActivity();
        Spacer(modifier = Modifier.height(6.dp))
        ExtraCurricularActivity();

    }
}

@Composable
fun ExtraCurricularActivity() {
    Row() {
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
                color = colortext,

                )
            Text(
                text = "Captain",
                fontFamily = fontFamily,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

        }
    }
}

@Composable
fun AchievementsDetail() {
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
        Spacer(modifier = Modifier.height(6.dp))
        Achievement();
        Spacer(modifier = Modifier.height(6.dp))
        Achievement();

    }
}

@Composable
fun Achievement() {
    Row() {
        Image(
            painter = painterResource(id = R.drawable.dps),
            contentDescription = "dps",

            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Inter school Championship",
                fontFamily = fontFamily,
                color = colortext,

                )
            Text(
                text = "Man of the Match",
                fontFamily = fontFamily,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

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