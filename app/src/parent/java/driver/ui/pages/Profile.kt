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
import driver.headingColor

val fontFamily = FontFamily.SansSerif
@Composable
fun Profile(navController: NavController) {
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
        Column(modifier=Modifier.padding(start=24.dp)) {
            ExperienceDetail()
            EducationDetail()
            ExtraCurricularActivitiesDetail()
            AchievementsDetail();

        }
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
            .padding(start = 24.dp)
    ) {
        Text(
            text = "Krish Chauhan",
            fontSize = 24.sp,

            fontFamily = fontFamily,
            color = headingColor
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
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "EXPERIENCE",
            fontSize = 14.sp,
            fontFamily = fontFamily,

            color = headingColor,

            )
        Experience();
        Experience();

    }
}

@Composable
fun Experience() {
    Spacer(modifier = Modifier.height(8.dp))
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
                    color = headingColor,

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
    }
}

@Composable
fun EducationDetail() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "EDUCATION",
            fontSize = 14.sp,
            fontFamily = fontFamily,

            color = headingColor,

            )
        Education();
        Education();

    }
}

@Composable
fun Education() {
    Spacer(modifier = Modifier.height(8.dp))
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
                color = headingColor,

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
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "EXTRA CURRICULAR ACTIVITIES",
            fontSize = 14.sp,
            fontFamily = fontFamily,

            color = headingColor,

            )
        ExtraCurricularActivity();
        ExtraCurricularActivity();

    }
}

@Composable
fun ExtraCurricularActivity() {
    Spacer(modifier = Modifier.height(8.dp))
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
                color = headingColor,

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
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "ACHIEVEMENTS",
            fontSize = 14.sp,
            fontFamily = fontFamily,

            color = headingColor,

            )
        Achievement();
        Achievement();

    }
}

@Composable
fun Achievement() {
    Spacer(modifier = Modifier.height(8.dp))
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
                color = headingColor,

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