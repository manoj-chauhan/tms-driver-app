package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.CarCrash
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PostAdd
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.drishto.driver.R
import driver.models.PostsFeed
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(

    username: String,
    profileImageResId: Int,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        TopAppBar(

            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = username,
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        IconButton(onClick = onSearchClick) {
                            Icon(Icons.Outlined.Search, contentDescription = "Search")
                        }

                        IconButton(onClick = onNotificationClick) {
                            Icon(Icons.Outlined.Notifications, contentDescription = "Notifications")
                        }

                        IconButton(onClick = onProfileClick) {
                            Image(
                                painter = painterResource(id = profileImageResId),

                                contentDescription = "User Profile",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)

                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
        )
    }
}

@Composable
fun BottomNavBar(
    onTabSelected:(selectedIndex:Int)->Unit,
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
        tonalElevation = 10.dp,
//        modifier = Modifier.shadow(60.dp, RoundedCornerShape(8.dp)),
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
                            contentDescription = bottomNavItem.title,
                            tint =  Color.DarkGray

                            )
                    }


                },
                label = { Text(text = bottomNavItem.title) }

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
    Scaffold(

        bottomBar = {
            Box(
                modifier = Modifier
//                    .padding(6.dp)
                    .shadow(20.dp)
            ) {


                BottomNavBar(
                    onTabSelected = {selectedIndex =it  },
                    onTripsClick = onTripsClick,
                    onEventsClick = onEventsClick,
                    onHomeClick = onHomeClick,
                    onNoticesClick = onNoticesClick,
                    onSettingsClick = onSettingsClick
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    TopBar(

                        username = "Krish Chauhan",
                        profileImageResId = R.drawable.boy,
                        onSearchClick = { },
                        onNotificationClick = { },
                        onProfileClick = { }
                    )
                }
                when (selectedIndex){
                    2->{
                        item{
                            PostsSection(profileId,navigationController, onCommentClick={})
                        }
                    }
                }


            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun PreviewMainScreen() {
//    MainScreen(
//
//        onTripsClick = { },
//        onEventsClick = { },
//        onHomeClick = { },
//        onNoticesClick = { },
//        onSettingsClick = { }
//
//    )
//}