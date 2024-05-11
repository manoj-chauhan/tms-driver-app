package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.drishto.driver.R
import driver.models.Event
import driver.models.PostsFeed
import driver.ui.components.ProfileDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(

    username: String,
    profileImageResId: Int,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val fontFamily = FontFamily.SansSerif
    val first = Color(android.graphics.Color.parseColor("#1c1b1f"))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(
                            text = username,
                            fontSize = 18.sp,
                            color = first,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W400,
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .clickable {
                                    onSearchClick()
                                }
                        )

                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .clickable {
                                    onNotificationClick()
                                }
                        )

//                        Box(
//                            modifier = Modifier
//                                .background(Color.Gray, shape = CircleShape)
//                                .width(30.dp)
//                                .align(Alignment.CenterVertically)
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.atul),
//                                contentDescription = "Edit Icon",
//                                modifier = Modifier
//                                    .size(28.dp)
//                                    .clip(CircleShape)
//                                    .clickable { onProfileClick() }
//                                    .align(Alignment.Center)
//                            )
//                        }
                    }
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
        )
    }
}

@Composable
fun BottomNavBar(

    onTabSelected: (selectedIndex: Int) -> Unit,
    onTripsClick: () -> Unit,
    onEventsClick: () -> Unit,
    onHomeClick: () -> Unit,
    onNoticesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val bottomBar = listOf(
        NavigationItem(
            title = "Trips",
            selectedIcon = Icons.Filled.DirectionsBus,
            unselectedIcon = Icons.Outlined.DirectionsBus,
            badgeCount = 0,
            navigate = "MainScreen"
        ),
        NavigationItem(
            title = "Events",
            selectedIcon = Icons.Filled.Event,
            unselectedIcon = Icons.Outlined.Event,
            badgeCount = 0,
            navigate = "events"
        ),
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,

            badgeCount = 0,
            navigate = "add-Institute"
        ),
        NavigationItem(
            title = "Notices",
            selectedIcon = Icons.Filled.Dashboard,
            unselectedIcon = Icons.Outlined.Dashboard,
            badgeCount = 45,
            navigate = "notice_lists"

        ),

        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            badgeCount = 0,
            navigate = "post_page"


        ),

        )
    var selected by remember {
        mutableStateOf(2)
    }
    NavigationBar(

        tonalElevation = 20.dp,
        modifier = Modifier.height(60.dp),
        containerColor = Color.White,
    ) {
        bottomBar.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = index == selected,
                onClick = {
                    selected = index
                    onTabSelected(index)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (bottomNavItem.badgeCount != 0) {
                                Badge {
                                    Text(text = bottomNavItem.badgeCount.toString())
                                }
                            }

                        }

                    ) {
                        Icon(
                            imageVector = if (index == selected) {
                                bottomNavItem.selectedIcon
                            } else {
                                bottomNavItem.unselectedIcon
                            },
                            modifier = Modifier.size(20.dp),
                            contentDescription = bottomNavItem.title,
                            tint = Color.DarkGray

                        )
                    }


                },
                label = {
                    Text(
                        text = bottomNavItem.title,
                        style = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.W400
                        ),
                    )
                }

            )


        }
    }
}

@Composable
fun MainScreen(
    profileId: String,
    navigationController: NavHostController,
    onCommentClick: (postData: PostsFeed) -> Unit,
    onTripsClick: () -> Unit,
    onEventsClick: () -> Unit,
    onHomeClick: () -> Unit,
    onNoticesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {


    var selectedIndex by remember {
        mutableStateOf(2)
    }
    var eventDetail by remember { mutableStateOf<Event?>(null) }

    var profileDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
//                    .padding(6.dp)


                    .shadow(78.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavBar(

                    onTabSelected = { selectedIndex = it },
                    onTripsClick = onTripsClick,
                    onEventsClick = onEventsClick,
                    onHomeClick = onHomeClick,
                    onNoticesClick = onNoticesClick,
                    onSettingsClick = onSettingsClick
                )
            }
        },
        floatingActionButton = {
            if (selectedIndex != 0 && selectedIndex != 4) {
                FloatingActionButton(
                    onClick = { },
                ) {
                    Icon(Icons.Outlined.ModeEdit, contentDescription = "Add")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                item {
                    Row(
                        modifier = Modifier.height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopBar(
                            username = "Krish Chauhan",
                            profileImageResId = R.drawable.boy,
                            onSearchClick = { },
                            onNotificationClick = { },
                            onProfileClick = { profileDialog = true }
                        )
                    }
                }
                when (selectedIndex) {
                    0 -> {
                        item { NewTripsDesign() }
                    }

                    1 -> {
                        item {
                            EventsListPage(
                                navigationController, onRegisterClick = {
                                    eventDetail = it
                                    navigationController.navigate("event-details")
                                }
                            )
                        }
                    }

                    2 -> {
                        item {
                            PostsSection(profileId, navigationController, onCommentClick = {
                                navigationController.navigate("add_comment/${it.id}")
                            })
                        }
                    }

                    3 -> {
                        item {
                            NoticeListPage()

                        }
                    }

                    4 -> {
                        item { SettingsPage() }


                    }
                }
            }
        }
    }
    if (profileDialog) {
        ProfileDialog {
            profileDialog = it
        }
    }
}