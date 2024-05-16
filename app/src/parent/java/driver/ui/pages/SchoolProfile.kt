package driver.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R
import driver.greybackground
import driver.textColor

@Composable
fun schoolProfile() {
    val gry = Color(android.graphics.Color.parseColor("#838383"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {

        Column(modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
            ) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    Image(
                        painter = painterResource(id = R.drawable.hi),
                        contentDescription = "Background Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(start = 15.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent,
                        modifier = Modifier
                            .size(150.dp),
                        border = BorderStroke(2.dp, Color.LightGray),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.White)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.Transparent,
                                modifier = Modifier
                                    .size(120.dp)
                                    .align(Alignment.Center),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dps),
                                    contentDescription = "Profile Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }
                }


            }
            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = "Delhi Public School",
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W400)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = "@virus",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W400, color = gry)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Text(
                    text = "We believe in nuturing the future of country by contributing to education initiatives and promoting quality in education.",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W300, color = textColor)
                )

            }

            Spacer(modifier = Modifier.height(60.dp))
            HorizontalDivider(
                modifier = Modifier
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(greybackground)
            ) {

                ProfileSections()

            }
        }
    }
}

@Composable
fun ProfileSections() {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabItems = listOf(
        tabItem("About"), tabItem("Reviews"), tabItem("Posts")
    )
    Column(modifier = Modifier.fillMaxSize()) {
        PostsTabView(modifier = Modifier, tabItems, onTabSelected = { index: Int ->
            selectedTabIndex = index
        })

        when (selectedTabIndex) {
            0 -> {
                AboutSectionProfile()

            }
        }
    }
}

@Composable
fun AboutSectionProfile() {

        ContentAbout()

}

@Composable
fun ContentAbout() {
    val gry = Color(android.graphics.Color.parseColor("#838383"))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))


        address()
        Spacer(modifier = Modifier.height(20.dp))
        contacts()

    }
}


@Composable
fun address(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, top = 16.dp, bottom = 16.dp)
                .background(Color.White)
        ) {
            Text(
                text = "ADDRESS",
                style = TextStyle(fontSize = 16.sp, fontFamily = fontFamily)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "243 Merut Road, Near GSK Factory, Sonipath, Haryana, 243954",
                style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.W300, color = textColor
                )
            )
        }
    }
}

@Composable
fun contacts(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, top = 16.dp, bottom = 16.dp)
                .background(Color.White)
        ) {
            Text(
                text = "CONTACTS",
                style = TextStyle(fontSize = 16.sp, fontFamily = fontFamily)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "abcde@example.com", style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.W300, color = textColor
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "+91-7543894334", style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.W300, color = textColor
                )
            )

        }
    }
}



@Composable
@Preview
fun schoolProfilePreview() {
    schoolProfile()
}