package driver.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import driver.models.Notice_List
//import driver.models.getDummyNotices
import kotlinx.coroutines.launch

enum class NoticeTabs(
    val text: String
) {
    All(
        text = "All"
    ),
    Saved(
        text = "Saved"
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SavedNoticesPage(navController: NavHostController) {
    val primary = Color(0xFF92A3FD)
    val secondary = Color(0XFF9DCEFF)

    var selectedTabIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { NoticeTabs.entries.size })

    Scaffold(
        topBar = { Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(modifier = Modifier
                .width(60.dp)
                .align(Alignment.CenterVertically)) {
                IconButton(modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.Center), onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Notices", style =  TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }

            Box(modifier = Modifier.width(100.dp).padding(end = 10.dp)) {
                Button(
                    modifier = Modifier
                        .height(25.dp),
                    enabled = true,
                    onClick = {
                        navController.navigate("add-Notice-Form")
                    },
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(35.dp)
                            .align(Alignment.Bottom)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        primary,
                                        secondary
                                    )
                                ),
                                shape = RoundedCornerShape(1.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Add Notice",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

        } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth()
            ) {
                NoticeTabs.entries.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(tab.ordinal)
                            }
                        },
                        text = { Text(tab.text) }
                    )
                }
            }

            when (selectedTabIndex) {

                0 -> {
//                    NoticeListPage(
//
//                        onReadClick = {},
//                        onDownloadClick = {},
//                    )
                }
                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {

                    }

                }
            }
        }
    }
}