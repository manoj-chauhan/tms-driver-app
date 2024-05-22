package driver.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.drishto.driver.R
import driver.models.PostsFeed
import driver.ui.pages.videoPlayer


@Composable
fun MediaViewerDialog(
    post: PostsFeed,
    setShowDialog: (Boolean) -> Unit,
    images: List<String>
) {


    var likesCount by remember { mutableIntStateOf(post?.likes ?: 0) }
    var isLiked by remember { mutableStateOf(post?.likedStatus) }

    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f)),

                ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp)
                                    .height(25.dp)
                                    .clickable {
                                        setShowDialog(false)
                                    },
                                tint = Color.White
                            )
                        }

                        Text(
                            text = post.userName, style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W500,
                                color = Color.White
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp)
                                .height(25.dp)
                                .clickable {
                                    setShowDialog(false)
                                },
                            tint = Color.White
                        )
                    }
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                        DialogMedia(images)
                    }

                    HorizontalDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
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
                            if (liked == true) 1.0f else 0.8f
                        }

                        if (isLiked == true) {
                            Row(
                                modifier = Modifier.width(60.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.likenew),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clickable {
//                                        post?.let { postActionViewModel.dislikePost(it.id) }
                                            likesCount--
                                            isLiked = false
                                        }
                                        .scale(scale),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = "${likesCount}",
                                    style = TextStyle(fontSize = 14.sp, color = Color.White)
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.width(60.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.like_white),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clickable {
                                            if (post != null) {
//                                            postActionViewModel.likePost(postsFeed.id)
                                            }
                                            likesCount++
                                            isLiked = true
                                        }
                                        .scale(scale),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = "${likesCount}",
                                    style = TextStyle(fontSize = 14.sp, color = Color.White)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.width(60.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Message,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
//                                        onCommentClick(post)
                                    },
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${post?.comments}",
                                style = TextStyle(fontSize = 14.sp, color = Color.White)
                            )

                        }

                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
//                                        onCommentClick(post)
                                },
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DialogMedia(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = {
        images.size
    })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxHeight(0.8f)
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()

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
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { index ->
                val color =
                    if (pagerState.currentPage == index) Color.Cyan else Color.LightGray
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





