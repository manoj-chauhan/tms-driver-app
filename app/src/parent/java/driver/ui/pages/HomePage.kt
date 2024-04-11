package driver.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {

        var expander by remember {
            mutableStateOf(false)
        }

        var text by remember {
            mutableStateOf("")
        }

        var active by remember {
            mutableStateOf(false)
        }

        val gry = Color(android.graphics.Color.parseColor("#838383"))

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Scaffold(
                    modifier = Modifier,
                    topBar = {
                        Row(
                            modifier = Modifier.padding(top = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(modifier = Modifier, onClick = { expander = true }) {
                                Icon(
                                    modifier = Modifier.padding(top = 5.dp),
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = null
                                )
                            }

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                value = text,
                                onValueChange = {
                                    text = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 16.sp
                                ),
                                label = {
                                    Text("Search")
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                    }
                                ),
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = Color.Black
                                ),
                            )

                            Box(
                                modifier = Modifier
                                    .background(Color.White, shape = CircleShape)
                                    .padding(top = 4.dp)
                                    .size(38.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Edit Icon",
                                    tint = gry,
                                    modifier = Modifier
                                        .size(27.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }, content = {
                        it

                    }

                )
            }
            Spacer(modifier = Modifier.height(1.dp))

            val tabItems = listOf(
                tabItem("All"), tabItem("School"), tabItem("Class")
            )

            var selectedTabIndex by remember { mutableStateOf(0) }
            Box(modifier = Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.height(43.dp)) {
                            TabRow(selectedTabIndex = selectedTabIndex) {
                                tabItems.forEachIndexed { index: Int, item ->
                                    Tab(selected = index == selectedTabIndex,
                                        modifier = Modifier
                                            .height(40.dp)
                                            .padding(bottom = 20.dp),
                                        onClick = { selectedTabIndex = index },
                                        text = {
                                            Row(
                                                modifier = Modifier.fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(text = item.title)
                                            }
                                        },
                                        icon = {

                                        }
                                    )
                                }
                            }
                        }
                        val pagerState = rememberPagerState {
                            tabItems.size
                        }

                        LaunchedEffect(selectedTabIndex) {
                            pagerState.animateScrollToPage(selectedTabIndex)

                        }
                        LaunchedEffect(pagerState.currentPage) {
                            selectedTabIndex = pagerState.currentPage
                        }

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .background(Color.LightGray)
                        )

                        HorizontalPager(
                            state = pagerState, modifier = Modifier
                                .fillMaxWidth()
                        ) { index ->
                            Box(modifier = Modifier.fillMaxSize()) {
                                Column {


                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 10.dp, top = 10.dp)
                                        ) {
                                            Column(modifier = Modifier) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 10.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .background(
                                                                Color.White,
                                                                shape = CircleShape
                                                            )
                                                            .size(44.dp)
                                                    ) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.atul),
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .width(200.dp)
                                                                .height(200.dp)
                                                                .clip(CircleShape)
                                                                .border(
                                                                    width = 0.dp,
                                                                    Color.White,
                                                                    shape = CircleShape
                                                                ),
                                                            contentScale = ContentScale.FillBounds
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(14.dp))

                                                    Column(modifier = Modifier.fillMaxWidth()) {
                                                        Box(modifier = Modifier.fillMaxWidth()) {
                                                            Text(
                                                                text = "Delhi Public School",
                                                                style = TextStyle(
                                                                    fontSize = 16.sp,
                                                                    fontFamily = FontFamily.SansSerif
                                                                )
                                                            )
                                                        }
                                                        Box(modifier = Modifier.fillMaxWidth()) {
                                                            Text(
                                                                text = "Sonipath, Haryana",
                                                                style = TextStyle(
                                                                    fontSize = 14.sp,
                                                                    fontFamily = FontFamily.SansSerif,
                                                                    color = Color.Gray
                                                                )
                                                            )
                                                        }
                                                    }

                                                }

                                                Spacer(modifier = Modifier.size(15.dp))

                                                Row(modifier = Modifier.fillMaxWidth()) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.hi),
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .fillMaxHeight(0.3f),
                                                        contentScale = ContentScale.FillBounds
                                                    )
                                                }

                                                Spacer(modifier = Modifier.size(15.dp))

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(20.dp)
                                                        .padding(
                                                            vertical = 0.dp,
                                                            horizontal = 10.dp
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Row {
                                                        Text(
                                                            text = "12 comments",
                                                            style = TextStyle(fontSize = 12.sp, color = gry)
                                                        )

                                                        Spacer(modifier = Modifier.width(12.dp))

                                                        Text(
                                                            text = "12 likes",
                                                            style = TextStyle(fontSize = 12.sp, color = gry)
                                                        )
                                                    }

                                                    Row {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.like),
                                                            contentDescription = "",
                                                            modifier = Modifier.size(20.dp),
                                                            contentScale = ContentScale.FillBounds
                                                        )

                                                        Spacer(modifier = Modifier.width(12.dp))

                                                        Image(
                                                            painter = painterResource(id = R.drawable.message),
                                                            contentDescription = "",
                                                            modifier = Modifier.size(20.dp),
                                                            contentScale = ContentScale.FillBounds
                                                        )

                                                        Spacer(modifier = Modifier.width(10.dp))

                                                        Image(
                                                            painter = painterResource(id = R.drawable.share),
                                                            contentDescription = "",
                                                            modifier = Modifier.size(20.dp),
                                                            contentScale = ContentScale.FillBounds
                                                        )

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.size(15.dp))

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 10.dp, top = 10.dp)
                                        ) {
                                            Column(modifier = Modifier) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 10.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .background(
                                                                Color.White,
                                                                shape = CircleShape
                                                            )
                                                            .size(44.dp)
                                                    ) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.atul),
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .width(200.dp)
                                                                .height(200.dp)
                                                                .clip(CircleShape)
                                                                .border(
                                                                    width = 0.dp,
                                                                    Color.White,
                                                                    shape = CircleShape
                                                                ),
                                                            contentScale = ContentScale.FillBounds
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(14.dp))

                                                    Column(modifier = Modifier.fillMaxWidth()) {
                                                        Box(modifier = Modifier.fillMaxWidth()) {
                                                            Text(
                                                                text = "Delhi Public School",
                                                                style = TextStyle(
                                                                    fontSize = 16.sp,
                                                                    fontFamily = FontFamily.SansSerif
                                                                )
                                                            )
                                                        }
                                                        Box(modifier = Modifier.fillMaxWidth()) {
                                                            Text(
                                                                text = "Sonipath, Haryana",
                                                                style = TextStyle(
                                                                    fontSize = 14.sp,
                                                                    fontFamily = FontFamily.SansSerif,
                                                                    color = Color.Gray
                                                                )
                                                            )
                                                        }
                                                    }

                                                }

                                                Spacer(modifier = Modifier.size(15.dp))

                                                Row(modifier = Modifier.fillMaxWidth()                                                        .padding(horizontal = 10.dp)
                                                    .padding(horizontal = 1.dp)
                                                ) {
                                                    Text(
                                                        text = "Congratulations!! to all the winners of the Inter School Competition 2024." +
                                                                "" +
                                                                "This add another chapter to in the history of out school, where we our belief in hard work and commitment helps our students to achieve new benchmarks.",
                                                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400)
                                                    )

                                                }

                                                Spacer(modifier = Modifier.size(15.dp))

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(20.dp)
                                                        .padding(
                                                            vertical = 0.dp,
                                                            horizontal = 10.dp
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Row {
                                                        Text(
                                                            text = "12 comments",
                                                            style = TextStyle(fontSize = 12.sp, color = gry)
                                                        )

                                                        Spacer(modifier = Modifier.width(12.dp))

                                                        Text(
                                                            text = "12 likes",
                                                            style = TextStyle(fontSize = 12.sp, color = gry)
                                                        )
                                                    }

                                                    Row {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.like),
                                                            contentDescription = "",
                                                            modifier = Modifier.size(20.dp),
                                                            contentScale = ContentScale.FillBounds
                                                        )

                                                        Spacer(modifier = Modifier.width(12.dp))

                                                        Image(
                                                            painter = painterResource(id = R.drawable.message),
                                                            contentDescription = "",
                                                            modifier = Modifier.size(20.dp),
                                                            contentScale = ContentScale.FillBounds
                                                        )

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class tabItem(
    val title: String
)

@Preview
@Composable
fun HomePagePreview() {
    HomePage()
}