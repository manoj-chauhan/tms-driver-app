package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
val primary = Color(android.graphics.Color.parseColor("#6750a4"))
val color = Color(android.graphics.Color.parseColor("#828282"))
val school = Color(android.graphics.Color.parseColor("#a1a1a1"))
val fontFamily = FontFamily.SansSerif

val colortext= Color(android.graphics.Color.parseColor("#1c1b1f"))

@Composable
fun NoticeCard(notice: NoticeAll) {
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
                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = notice.text,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = fontFamily,
                            color = colortext,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = notice.institue,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = fontFamily,
                            color = school,
                            fontWeight = FontWeight.W400
                        )
                    )

                }
//                Spacer(modifier = Modifier.height(9.dp))

                Row (modifier=Modifier.padding(top=2.dp)){

                    Text(
                        text = notice.date,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            color = color,
                            fontWeight = FontWeight.W400
                        )
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = notice.time,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            color = color,
                            fontWeight = FontWeight.W400
                        )
                    )
                }
            }
        }

        val imageHeight = if (notice.imageResId == R.drawable.doc) {
            150.dp
        } else {
            220.dp
        }
        val paddingtop = if (notice.imageResId == R.drawable.doc) {
            30.dp
        } else {
            0.dp
        }
        val paddingbottom = if (notice.imageResId == R.drawable.doc) {
            30.dp
        } else {
            0.dp
        }
        Box(modifier = Modifier
            .height(220.dp)
            .background(Color.White)
            .align(Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(id = notice.imageResId),
                contentDescription = "Notice Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .padding(top = paddingtop)
                    .padding(bottom = paddingbottom)
                    .aspectRatio(16f / 9f)
            )

        }


    }
    Spacer(modifier=Modifier.height(10.dp))
}







@Composable
fun NoticeListPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()

            .padding(10.dp),

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
        date = "22 Jun'24",
        time = "09:00 AM",
        imageResId = R.drawable.notice1
    ),
    NoticeAll(
        text = "School Picnic",
        institue = "HMRITM",
        date = "23 June'24",
        time = "10:00 AM",
        imageResId = R.drawable.notice2
    ),
    NoticeAll(
        text = "Annual Day Celebration",
        institue = "Maharaja Agresen Public School",
        date = "24 Jun'24",
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



