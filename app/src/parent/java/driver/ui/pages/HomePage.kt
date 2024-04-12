package driver.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
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
import androidx.compose.ui.text.input.VisualTransformation
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
        LazyColumn {
            item {
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
                                    modifier = Modifier
                                        .padding(top = 2.dp)
                                        .fillMaxHeight(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    IconButton(modifier = Modifier, onClick = { expander = true }) {
                                        Icon(
                                            modifier = Modifier,
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = null
                                        )
                                    }

                                    val colors = TextFieldDefaults.textFieldColors()
                                    val interactionSource = remember { MutableInteractionSource() }


                                    Box(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        BasicTextField(
                                            value = text,
                                            onValueChange = { text = it },
                                            textStyle = TextStyle(fontSize = 20.sp),
                                            modifier = Modifier
                                                .indicatorLine(
                                                    enabled = true,
                                                    isError = false,
                                                    interactionSource = interactionSource,
                                                    colors = colors,
                                                    focusedIndicatorLineThickness = 0.dp,
                                                    unfocusedIndicatorLineThickness = 0.dp
                                                )
                                                .height(35.dp).fillMaxWidth(0.96f),

                                            enabled = true,
                                            singleLine = true,
                                            decorationBox = { innerTextField ->
                                                TextFieldDefaults.TextFieldDecorationBox(
                                                    value = text,
                                                    innerTextField = innerTextField,
                                                    visualTransformation = VisualTransformation.None,
                                                    trailingIcon = { /* ... */ },
                                                    placeholder = {
                                                        Text(
                                                            text = "Search",
                                                            fontSize = 14.sp,
                                                        )
                                                    },
                                                    colors = TextFieldDefaults.textFieldColors(
                                                        containerColor = Color.LightGray,
                                                        focusedIndicatorColor = Color.Transparent,
                                                        unfocusedIndicatorColor = Color.Transparent
                                                    ),
                                                    singleLine = true,
                                                    enabled = true,
                                                    interactionSource = interactionSource,
                                                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                                                        top = 0.dp,
                                                        bottom = 0.dp
                                                    ),
                                                    shape = RoundedCornerShape(16.dp)
                                                )
                                            }

                                        )
                                    }
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
                                TabRow(
//                                    backgroundColor = Color.Transparent.copy(0.1f),
                                    selectedTabIndex = selectedTabIndex,
                                ) {
                                    tabItems.forEachIndexed { index: Int, item ->
                                        Tab(selected = index == selectedTabIndex,
                                            onClick = { selectedTabIndex = index },
                                            text = {
                                                Text(
                                                    text = item.title,
                                                    style = TextStyle(
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.W400
                                                    ),
                                                )
                                            }
                                        )
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

                                    Column {
                                        ContentPage()
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


@Composable
fun ContentPage() {

    val gry = Color(android.graphics.Color.parseColor("#838383"))
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
//            .clickable { onClick(trip) }
    ) {
        Column {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                    //                verticalArrangement = Arrangement.SpaceBetween
                ) {

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
                                .height(200.dp),
                            contentScale = ContentScale.Crop
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

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                    //                verticalArrangement = Arrangement.SpaceBetween
                ) {

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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp)
                            .padding(horizontal = 10.dp)
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
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                    //                verticalArrangement = Arrangement.SpaceBetween
                ) {

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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp)
                            .padding(horizontal = 10.dp)
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