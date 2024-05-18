package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.drishto.driver.R
import driver.headingColor
import driver.models.Notice_List
import driver.textColor
import driver.ui.viewmodels.NoticesViewModel
import java.time.format.DateTimeFormatter


@Composable
fun NoticeCard(notice: Notice_List) {
    val fontFamily = FontFamily.SansSerif
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    ElevatedCard(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(12.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp)
                    .padding(7.5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = notice.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = fontFamily,
                            color = headingColor,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = notice.instituteName,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = fontFamily,
                            color = textColor,
                            fontWeight = FontWeight.W400
                        )
                    )
                }

                Row(modifier = Modifier.padding(top = 2.dp)) {
                    Text(
                        text = notice.dateOfNotice.format(dateFormatter),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            color = textColor,
                            fontWeight = FontWeight.W400
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = notice.timeOfNotice,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            color = textColor,
                            fontWeight = FontWeight.W400
                        )
                    )
                }
            }
        }

        val imageHeight = if (notice.media?.type == "doc") {
            150.dp
        } else {
            220.dp
        }
        val paddingTop = if (notice.media?.type == "doc") {
            30.dp
        } else {
            0.dp
        }
        val paddingBottom = if (notice.media?.type == "doc") {
            30.dp
        } else {
            0.dp
        }
        Box(
            modifier = Modifier
                .height(220.dp)
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
        ) {
            notice.media?.mediaUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Notice Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(imageHeight)
                        .padding(top = paddingTop, bottom = paddingBottom)
                        .aspectRatio(16f / 9f)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun NoticeListPage(
    navigation: NavHostController,
    onReadClick: (Notice_List) -> Unit,
    onDownloadClick: (String) -> Unit
) {
    val noticesViewModel: NoticesViewModel = hiltViewModel()
    val notices by noticesViewModel.noticelist.collectAsStateWithLifecycle()

    noticesViewModel.getAllNotices()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        notices?.let { noticeList ->
            noticeList.forEach { notice ->
                NoticeCard(
                    notice = notice,
//                    onReadClick = { onReadClick(notice) },
//                    onDownloadClick = onDownloadClick
                )
            }
        }
    }
}




