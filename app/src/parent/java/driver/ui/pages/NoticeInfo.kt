package driver.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import driver.models.Image
import driver.models.Media
import driver.models.Notice_List
import driver.ui.viewmodels.EventsViewModel
import driver.ui.viewmodels.NoticesViewModel


@Composable
fun NoticeDetail(noticeId: String?, navController: NavHostController) {

    val noticesViewModel: NoticesViewModel = hiltViewModel()

    val noticeDetail by noticesViewModel.noticelist.collectAsState()

    val onBackPressed: () -> Unit = {
        navController.navigateUp()
    }

    val typography = MaterialTheme.typography

    LaunchedEffect(Unit) {
        noticeId?.let { id ->
            noticesViewModel.getNoticeById(id)
        }
    }

    NoticeDetailPage()



    }

@Composable
fun NoticeDetailPage(notice: Notice_List) {\

    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        notice.media?.mediaUrl?.let { mediaUrl ->
            AyncImage(
               model=Media.mediaUrl,
                contentDescription = "Notice Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
//            AsyncImage(
//
//                model = Media.mediaUrl,
//                contentDescription = null,
//                modifier = Modifier.fillMaxWidth(),
//                contentScale = ContentScale.Crop
//            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = notice.title,
            style = typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Text(
            text = notice.description,
//            style = typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

//@Preview
//@Composable
//fun NoticeDetailPage(){
//
//}


