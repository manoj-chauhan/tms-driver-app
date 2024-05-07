package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R

data class NoticeAll(
    val text: String,
    val institue:String,
    val date: String,
    val time: String,
    val imageResId: Int
)

@Composable
fun NoticeCard(notice: NoticeAll) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(8.dp)
            .shadow(3.dp, RoundedCornerShape(12.dp))
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
                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = notice.text, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(text = notice.institue, fontSize = 10.sp, fontWeight = FontWeight.Light)

                }

                Row {
                    Text(text = notice.date, fontSize = 9.sp, fontWeight = FontWeight.Light)

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(text = notice.time, fontSize = 9.sp, fontWeight = FontWeight.Light)
                }
            }

            val imageHeight = if (notice.imageResId == R.drawable.doc) {
                100.dp
            } else {
                220.dp
            }
            val paddingtop= if (notice.imageResId == R.drawable.doc) {
                23.dp
            } else {
                0.dp
            }

            Image(
                painter = painterResource(id = notice.imageResId),
                contentDescription = "Notice Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .padding(top = paddingtop)
                    .aspectRatio(16f / 9f)
            )
        }
    }
}

@Composable
fun NoticeListPage() {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(start=6.dp)
            .padding(end=6.dp)
            .padding(bottom=6.dp)
            .padding(top=3.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
         noticeData.let{  notice->
             notice.forEach{
                 NoticeCard(it)

             }

        }
    }
}


val noticeData = listOf(
    NoticeAll(
        text = "Meeting with Parents",
        institue = "Maharaja Agrasen Public School",
        date = "22 June 2024",
        time = "09:00 AM",
        imageResId = R.drawable.notice1
    ),
    NoticeAll(
        text = "School Picnic",
        institue = "HMRITM",
        date = "23 June 2024",
        time = "10:00 AM",
        imageResId = R.drawable.notice2
    ),
    NoticeAll(
        text = "Annual Day Celebration",
        institue = "Maharaja Agresen Public School",
        date = "24 June 2024",
        time = "11:00 AM",
        imageResId = R.drawable.doc
    )
)
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewNoticeListPage() {
//    NoticeListPage(notices = noticeData)
//}



