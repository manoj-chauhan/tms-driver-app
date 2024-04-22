package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R

@Composable
fun schoolProfile() {
    val gry = Color(android.graphics.Color.parseColor("#838383"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
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
                            .size(150.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.atul),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)){
                Text(text = "Delhi Public School", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W400))
            }

            Spacer(modifier = Modifier.height(6.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)){
                Text(text = "@virus", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W400, color = gry))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)){
                Text(text = "We believe in nuturing the future of country by contributing to education initiatives and promoting quality in education.", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W600, color = gry))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)) {
                Spacer(modifier = Modifier.height(2.dp))

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
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            PostsTabView(modifier = Modifier, tabItems,onTabSelected = { index: Int ->
                selectedTabIndex = index
            })
        }
        when (selectedTabIndex) {
            0 -> {
                item {
//                    PostsSection()
                }
            }
        }
    }
}


@Composable
@Preview
fun schoolProfilePreview() {
    schoolProfile()
}