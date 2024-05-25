package driver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import driver.models.Notice_List
import driver.textColor
import driver.ui.viewmodels.NoticesViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun NoticeDetailPage(
    noticeId: String?,
    onReadClick: () -> Unit,
    onDownloadClick: () -> Unit
) {
    val noticesViewModel: NoticesViewModel = hiltViewModel()
    val noticedetail by noticesViewModel.noticeDetail.collectAsState()
    val fontFamily = FontFamily.SansSerif

    LaunchedEffect(Unit) {
        noticeId?.let { id ->
            noticesViewModel.getNoticeById(id)
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(13.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            noticedetail?.let { detail ->
                Text(
                    text = detail.title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )
                )

                Text(
                    text = detail.instituteName,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = fontFamily
                    )
                )

                // Date and time formatting
                val inputDateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
                val outputDateFormat = remember { SimpleDateFormat("dd MMM", Locale.getDefault()) }

                val inputTimeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }
                val outputTimeFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

                val parsedDate = remember(detail.dateOfNotice) { inputDateFormat.parse(detail.dateOfNotice) }
                val formattedDate = remember(parsedDate) { outputDateFormat.format(parsedDate!!) }

                val parsedTime = remember(detail.timeOfNotice) { inputTimeFormat.parse(detail.timeOfNotice) }
                val formattedTime = remember(parsedTime) { outputTimeFormat.format(parsedTime!!) }

                Text(
                    text = "Date: $formattedDate, Time: $formattedTime",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamily
                    )
                )

                detail.media?.let { media ->
                    NoticeImage(imageResId = media.mediaUrl)
                }

                Text(
                    text = detail.description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = fontFamily
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ReadButton(onReadClick = onReadClick)
                    DownloadButton(onDownloadClick = onDownloadClick)
                }
            }
        }
    }
}


@Composable
fun NoticeImage(imageResId: String) {
    AsyncImage(
        model = imageResId,
        contentDescription = "Notice Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.FillWidth,
    )
}


@Composable
fun ReadButton(onReadClick: () -> Unit) {
    Button(
        onClick = { onReadClick() },
        modifier = Modifier
//            .weight(1f)
            .padding(4.dp)
    ) {
        Text(text = "Read")
    }
}

@Composable
fun DownloadButton(onDownloadClick: () -> Unit) {
    Button(
        onClick = { onDownloadClick() },
        modifier = Modifier
//            .weight(1f)
            .padding(4.dp)
    ) {
        Text(text = "Download")
    }
}

