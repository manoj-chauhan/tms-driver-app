package driver.ui.pages

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.CarCrash
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.PostAdd
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.drishto.driver.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import driver.models.PostsFeed
import driver.ui.components.ExpandableText
import driver.ui.viewmodels.PostsViewModel
import kotlinx.coroutines.launch


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val navigate: String = ""
)

@Composable
fun PostsSection(
    navigationController: NavHostController,
    onCommentClick: (postData: PostsFeed) -> Unit
) {
    val pf: PostsViewModel = hiltViewModel()
    val postsList by pf.postFeedsDetails.collectAsStateWithLifecycle()
    pf.getPosts()

    Log.d("TAG", "PostsSection: $postsList")
    Column {
        postsList?.take(postsList!!.size)?.forEach { post ->
            ContentPage(post, navigationController, onCommentClick)
        }
    }
}

@Composable
fun PostsTabView(
    modifier: Modifier = Modifier,
    tabItems: List<tabItem>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    TabRow(
        selectedTabIndex = selectedTabIndex, modifier = modifier
    ) {
        tabItems.forEachIndexed { index, tabRowIcons ->
            Tab(selected = index == selectedTabIndex, onClick = {
                selectedTabIndex = index
                onTabSelected(index)
            }, text = {
                Text(
                    text = tabRowIcons.title,
                    style = TextStyle(
                        fontSize = 18.sp, fontWeight = FontWeight.W400
                    ),
                )
            }, selectedContentColor = Color.Black, unselectedContentColor = Color.Gray
            )
        }
    }
}


@Composable
fun ContentPage(
    post: PostsFeed,
    navigationController: NavHostController,
    onCommentClick: (postData: PostsFeed) -> Unit
) {

    val gry = Color(android.graphics.Color.parseColor("#838383"))
    val images = listOf(R.drawable.hi)



    Box(
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Column {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.fillMaxWidth(),
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
                                    Color.White, shape = CircleShape
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
                                        width = 0.dp, Color.White, shape = CircleShape
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Delhi Public School", style = TextStyle(
                                        fontSize = 16.sp, fontFamily = FontFamily.SansSerif
                                    )
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Sonipath, Haryana", style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color.Gray
                                    )
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.size(15.dp))
                    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)

                    ){
                        post.message?.let { ExpandableText(it) }
                    }
                    Spacer(modifier = Modifier.size(15.dp))


                    val images = post.media.map { it.mediaUrl }.toList()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        ImageScrollWithTextOverlay(images = images)

                    }

                    Spacer(modifier = Modifier.size(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(
                                vertical = 0.dp, horizontal = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(
                                text = "${post.comments} comments",
                                style = TextStyle(fontSize = 12.sp, color = gry)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "${post.likes} likes",
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
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        onCommentClick(post)
                                    },
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
        }
    }
}

data class tabItem(
    val title: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenNavigation(navController: NavHostController, onCommentClick : (postData: PostsFeed) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navigationController = rememberNavController()

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val topItems = listOf(
        NavigationItem(
            title = "Trips",
            selectedIcon = Icons.Outlined.CarCrash,
            unselectedIcon = Icons.Outlined.CarCrash,
            navigate = "MainScreen"
        ),
        NavigationItem(
            title = "Notices",
            selectedIcon = Icons.Outlined.Flag,
            unselectedIcon = Icons.Outlined.Flag,
            badgeCount = 45,
            navigate = "post_page"

        ),
        NavigationItem(
            title = "Events",
            selectedIcon = Icons.Outlined.BookmarkAdd,
            unselectedIcon = Icons.Outlined.BookmarkAdd,
            navigate = "add-Institute"
        ),
        NavigationItem(
            title = "Add post",
            selectedIcon = Icons.Outlined.PostAdd,
            unselectedIcon = Icons.Outlined.PostAdd,
            badgeCount = 45,
            navigate = "post_page"

        ),
        NavigationItem(
            title = "Add Institute",
            selectedIcon = Icons.Outlined.Business,
            unselectedIcon = Icons.Outlined.Business,
            navigate = "add-Institute"
        ),
    )
    val bottomItems = listOf(
        NavigationItem(
            title = "Give Feedback",
            selectedIcon = Icons.Outlined.Mail,
            unselectedIcon = Icons.Outlined.Mail,
            navigate = "add-Institute"
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Outlined.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            navigate = "add-Institute"
        ),
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    Column(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .height(80.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "DRISHTO",
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp,
                                color = Color.Red
                            )

                        }
                    }

                    Divider(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .height(2.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            topItems.forEachIndexed { index, item ->
                                NavigationDrawerItem(
                                    label = { Text(text = item.title) },
                                    icon = {
                                        Icon(
                                            imageVector = item.selectedIcon,
                                            contentDescription = "",
                                            modifier = Modifier.width(30.dp)
                                        )
                                    },
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        navController.navigate(item.navigate)
                                        selectedItemIndex = index
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    modifier = Modifier.height(40.dp)
                                )
                            }
                        }


                        Column(modifier = Modifier.padding(bottom = 8.dp)) {
                            bottomItems.forEachIndexed { index, item ->
                                NavigationDrawerItem(
                                    label = { Text(text = item.title) },
                                    icon = {
                                        Icon(
                                            imageVector = item.selectedIcon,
                                            contentDescription = "",
                                            modifier = Modifier.width(30.dp)
                                        )
                                    },
                                    selected = index + topItems.size == selectedItemIndex,
                                    onClick = {
                                        navController.navigate(item.navigate)
                                        selectedItemIndex = index + topItems.size
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    modifier = Modifier.height(40.dp)
                                )
                            }
                        }
                    }
                }
            }) {

            Scaffold(modifier = Modifier, topBar = {
                val scope = rememberCoroutineScope()

                val gry = Color(android.graphics.Color.parseColor("#838383"))
                var text by remember {
                    mutableStateOf("")
                }

                Row(
                    modifier = Modifier
                        .padding(top = 2.dp, end = 6.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(modifier = Modifier, onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
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
                        BasicTextField(value = text,
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
                                .height(35.dp)
                                .fillMaxWidth(0.96f),

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
                                        top = 0.dp, bottom = 0.dp
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                            }

                        )
                    }
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate("user_profile") }
                            .background(
                                Color.White, shape = CircleShape
                            )
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
            }, content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    Spacer(modifier = Modifier.height(6.dp))
                    HomeApp(modifier = Modifier, navController, onCommentClick)
                }
            }
            )
        }

    }

}

@Composable
fun HomeApp(
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    onCommentClick: (postData: PostsFeed) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabItems = listOf(
        tabItem("All"), tabItem("School"), tabItem("Class")
    )
    LazyColumn(modifier = modifier) {
        item {
            PostsTabView(modifier, tabItems, onTabSelected = { index: Int ->
                selectedTabIndex = index
            })
        }
        when (selectedTabIndex) {
            0 -> {
                item {
                    PostsSection(navigationController, onCommentClick)
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageScrollWithTextOverlay(images: List<String>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pagerState = rememberPagerState(pageCount = {
        images.size
    })


    Column(modifier = Modifier) {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
//                if(images[page].endsWith(".mp4")){
//                    videoPlayer(url = images[page])
//                }else {
                    AsyncImage(
                        model = images[page],
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
//                }

                Text(
                    text = "${page + 1}/${images.size}",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { index ->
                val color = if (pagerState.currentPage == index) Color.Cyan else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@Composable
fun videoPlayer(url: String) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(Uri.parse(url))

    exoPlayer.setMediaItem(mediaItem)
    exoPlayer.playWhenReady = true
    exoPlayer.prepare()

    val playerView = StyledPlayerView(context)
    playerView.player = exoPlayer

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = exoPlayer
                it.useController = false
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }

                else -> Unit
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}