package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.drishto.driver.R
import driver.Destination
import driver.models.PostsFeed


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(

    username: String,
    profileImageResId: Int,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    shadow: Int
) {
    val fontName = GoogleFont("Russo One")

    val fontFamily = FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )
    val logo = Color(android.graphics.Color.parseColor("#ef2427"))


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TopAppBar(
            modifier = Modifier.shadow(shadow.dp),
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.fillMaxWidth(0.78f)) {
                        Text(
                            text = "DRISHTO",
                            fontSize = 22.sp,
                            color = logo,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W700,
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
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
                    }
                }
            },
        )
    }
}

@Composable
fun BottomNavBar(
    selected: Int,
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
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            badgeCount = 0,
            navigate = "post_page"
        ),

        )

    NavigationBar(
        modifier = Modifier
            .height(60.dp)
            .shadow(4.dp),
        containerColor = Color.White,
    ) {
        bottomBar.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = index == selected,
                onClick = {
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

@OptIn(ExperimentalMaterial3Api::class)
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
    var selectedIndex by rememberSaveable { mutableStateOf(2) }
    var profileDialog by rememberSaveable { mutableStateOf(false) }
    val currentBackStackEntry by navigationController.currentBackStackEntryAsState()

    var selectedAssignmentCode by remember { mutableStateOf("") }
    var operatorId by remember { mutableIntStateOf(0) }
    var passengerTripId by remember { mutableIntStateOf(0) }

    val lazyListState = rememberLazyListState()
    val shadow by remember {
        derivedStateOf {
            if (lazyListState.firstVisibleItemScrollOffset > 0) 2 else 0
        }
    }

    LaunchedEffect(currentBackStackEntry) {
        val currentRoute = currentBackStackEntry?.destination?.route
        selectedIndex = when (currentRoute) {
            "trips" -> 0
            "events" -> 1
            "home" -> 2
            "notices" -> 3
            "settings" -> 4
            else -> selectedIndex
        }
    }

    Scaffold(
        topBar = {
            if (selectedIndex != 4)
                TopBar(
                    username = "Krish Chauhan",
                    profileImageResId = R.drawable.boy,
                    onSearchClick = { },
                    onNotificationClick = { },
                    onProfileClick = { profileDialog = true },
                    shadow = shadow
                )
        },
        bottomBar = {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavBar(
                    selected = selectedIndex,
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
                if (selectedIndex == 2) {
                    FloatingActionButton(onClick = { navigationController.navigate(Destination.PostPage) }) {
                        Icon(Icons.Outlined.Add, contentDescription = "Add")
                    }
                } else {
                    FloatingActionButton(onClick = { }) {
                        Icon(Icons.Outlined.ModeEdit, contentDescription = "Add")
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    when (selectedIndex) {
                        0 -> {
                            item {
                                NewTripsDesign(
                                    navigationController,
                                    onTripSelected = {
                                        selectedAssignmentCode = it.tripCode
                                        passengerTripId = it.passengerTripId
                                        operatorId = it.companyId
                                        navigationController.navigate(
                                            Destination.CurrentAssignmentDetail(
                                                selectedAssignmentCode,
                                                passengerTripId,
                                                operatorId
                                            )
                                        )
                                    },
                                    onPastTripSelected = {
                                        selectedAssignmentCode = it.tripCode
                                        operatorId = 1
                                        passengerTripId = it.passengerTripId
                                        navigationController.navigate(
                                            Destination.PastAssignmentDetail(
                                                selectedAssignmentCode,
                                                passengerTripId
                                            )
                                        )
                                    },
                                )
                            }
                        }

                        1 -> {
                            item {
                                Eventpage(navigationController, onRegisterClick = {
                                    navigationController.navigate(Destination.EventDetails(it))
                                })
                            }
                        }

                        2 -> {
                            item {
                                PostsSection(navigationController) {
                                    navigationController.navigate(Destination.AddComment(it.id))
                                }
                            }
                        }

                        3 -> {
                            item {
                                NoticeListPage(
                                    navigation = navigationController,
                                    onReadClick = {}) {

                                }
                            }
                        }

                        4 -> {
                            item {
                                SettingsPage(onProfileSelected = {
                                    navigationController.navigate(Destination.UserProfile)
                                }, navigationController)
                            }
                        }
                    }
                }
            }

        }
    }


}