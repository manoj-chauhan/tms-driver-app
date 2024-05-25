package driver.ui.pages

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.drishto.driver.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import driver.actionColors
import driver.headingColor
import driver.models.PostsFeed
import driver.subText
import driver.ui.components.MediaViewerDialog
import driver.ui.viewmodels.PostActionsViewModel
import driver.ui.viewmodels.PostsViewModel

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
    pf.getPosts()
    val postsList by pf.posts.collectAsStateWithLifecycle()

    val error by pf.error.collectAsStateWithLifecycle()

    if (error == "") {
        Column(modifier = Modifier.padding(10.dp)) {
            postsList?.take(postsList!!.size)?.forEach { post ->
                ContentPage(post, navigationController, onCommentClick)
            }
        }
    } else {
        Toast.makeText(LocalContext.current, error, Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun ContentPage(
    post: PostsFeed,
    navigationController: NavHostController,
    onCommentClick: (postData: PostsFeed) -> Unit,
    postActionViewModel: PostActionsViewModel = hiltViewModel()
) {
    var likesCount by remember { mutableStateOf(post.likes ?: 0) }
    var isLiked by remember { mutableStateOf(post.likedStatus) }
    var showDialog = remember { mutableStateOf(false) }
    val images = post.media.map { it.mediaUrl }



    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(12.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color.White, shape = CircleShape
                        )
                        .size(38.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dps),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Spacer(modifier = Modifier.width(9.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = post.userName,
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = headingColor,
                            fontFamily = FontFamily.SansSerif
                        )
                    )

                    Text(
                        text = "Sonipath, Haryana",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = subText
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            post.message?.let {
                Text(
                    text = it,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable { onCommentClick(post) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDialog.value=true }
            ) {
                ImageScrollWithTextOverlay(images = images)
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth(0.75f)) {
                    Text(
                        text = "${post.comments} comments",
                        style = TextStyle(fontSize = 14.sp, color = actionColors)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "$likesCount likes",
                        style = TextStyle(fontSize = 14.sp, color = actionColors)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val transition = updateTransition(targetState = isLiked)
                    val scale by transition.animateFloat(
                        transitionSpec = {
                            keyframes {
                                1.6f at 0
                                3.0f at 300
                                1.0f at 200
                            }
                        }, label = ""
                    ) { liked ->
                        if (liked) 1.0f else 0.8f
                    }

                    if (isLiked) {
                        Image(
                            painter = painterResource(id = R.drawable.likenew),
                            contentDescription = "",
                            modifier = Modifier
                                .size(23.dp)
                                .clickable {
                                    postActionViewModel.dislikePost(post.id)
                                    likesCount--
                                    isLiked = false
                                }
                                .scale(scale),
                            contentScale = ContentScale.FillBounds
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.like),
                            contentDescription = "",
                            modifier = Modifier
                                .size(23.dp)
                                .clickable {
                                    postActionViewModel.likePost(post.id)
                                    likesCount++
                                    isLiked = true
                                }
                                .scale(scale),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    Icon(
                        imageVector = Icons.Outlined.Message,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                onCommentClick(post)
                            }
                    )

                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                onCommentClick(post)
                            }
                    )
                }
            }
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
    if (showDialog.value) {
        MediaViewerDialog(
            post = post,
            images = images,
            setShowDialog = { showDialog.value = it }
        )
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
                if (images[page].endsWith(".mp4")) {
                    videoPlayer(url = images[page])
                } else {
                    AsyncImage(
                        model = images[page],
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                if (pagerState.pageCount > 1)
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
        if (pagerState.pageCount > 1) {
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
}

@Composable
fun videoPlayer(url: String) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val mediaItem = remember { MediaItem.fromUri(Uri.parse(url)) }

    var isPlaying by remember { mutableStateOf(false) }


    exoPlayer.setMediaItem(mediaItem)
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


    Box(modifier = Modifier.fillMaxSize()) {


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
        IconButton(
            onClick = {
                exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                isPlaying = !isPlaying
            },
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (exoPlayer.playWhenReady) "Pause" else "Play",
                modifier = Modifier
                    .size(100.dp)
                    .alpha(0.4f)
            )
        }
    }

}