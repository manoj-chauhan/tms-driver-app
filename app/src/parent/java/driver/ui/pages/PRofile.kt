package driver.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drishto.driver.R
import com.drishto.driver.ui.viewmodels.UserProfileViewModel
import driver.models.EducationList
import driver.ui.viewmodels.EducationListViewModel


@Composable
fun profile() {
    val vm: UserProfileViewModel = hiltViewModel()
    val userDetail by vm.userDetail.collectAsStateWithLifecycle()
     val context= LocalContext.current

    LaunchedEffect(Unit) {
        vm.userDetail(context)
    }
    val el: EducationListViewModel = hiltViewModel()
    val educationList by el.educationList.collectAsStateWithLifecycle()
    LaunchedEffect(userDetail) {
        el.getEducationList()
    }
    Log.d("user","$userDetail")


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.boy),

                    contentDescription = "student's photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "${userDetail?.name}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = "${userDetail?.userName}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black)

                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "EDUCATION",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                            , color = Color.Black)
                    }

                    educationList?.let {
                        driver.ui.pages.educationList(it)
                    }
                }
            }
        }
    }
}

@Composable
fun educationList(educationList:List<EducationList>){
    educationList?.forEach() { information ->


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 20.dp, bottom = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color.White, shape = CircleShape
                    )
                    .size(60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dps),
                    contentDescription = "",
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .clip(CircleShape)
                        .border(
                            width = 0.dp, Color.White, shape = CircleShape
                        ),
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = "${information.schoolName}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Text(
                        text = "${information.schoolAddress}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                    Text(
                        text = "${information.status}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )

                }

            }
        }
    }




}


@Composable
@Preview
fun proPreview() {
    profile()
}