package driver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import driver.models.MediaList



@Composable
fun MediaViewerDialog(
    shouldDisplay: Boolean,
//

    postId: String?,
    setShowDialog: (Boolean) -> Unit,
    mediaUrls: List<String>
) {


    val images= listOf("https://cdn.dribbble.com/users/5057709/screenshots/14446634/media/86166a1f60bf4afb28d1bfdf2b0421ed.jpg",
        "https://th.bing.com/th/id/OIP.mtHEExz1t7j105f38WZAngHaHa?w=626&h=626&rs=1&pid=ImgDetMain",
        "https://th.bing.com/th/id/OIP.mtHEExz1t7j105f38WZAngHaHa?w=626&h=626&rs=1&pid=ImgDetMain")

    val pagerState = rememberPagerState(pageCount = {
        images.size
    })





    if (shouldDisplay) {
        Dialog(onDismissRequest = { setShowDialog(false) }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                HorizontalPager(

                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val mediaUrl = mediaUrls[page]
                    Image(
                        painter = rememberAsyncImagePainter(mediaUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.Center),
                        contentScale = ContentScale.Fit

                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clickable(onClick = { setShowDialog(false) }),
                    tint = Color.White
                )


            }
        }
    }
}





