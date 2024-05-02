package driver.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import driver.models.getDummyNotices
import java.time.LocalDate


data class Notice(
    val title: String,
    val description: String,
    val date: LocalDate,
    val fileUrl: String? = null,
    val fileType: String? = null
)


@Composable
fun NoticeCard(
    notice: Notice,
    onReadClick: () -> Unit,
    onDownloadClick: (String) -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = notice.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Date: ${notice.date}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notice.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            if (notice.fileUrl != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Attached file:", style = MaterialTheme.typography.bodySmall)
                    IconButton(onClick = { onDownloadClick(notice.fileUrl) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.InsertDriveFile,
                            contentDescription = "Download File",
                            tint = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onReadClick) {
                Text("Read More")
            }
        }
    }
}

@Composable
fun NoticeListPage(

    onReadClick: (notice: Notice) -> Unit,
    onDownloadClick: (fileUrl: String) -> Unit
) {
    val notices= getDummyNotices()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notices) { notice ->
                NoticeCard(
                    notice,
                    onReadClick = { onReadClick(notice) },
                    onDownloadClick = onDownloadClick
                )
            }
        }
    }
}



@Preview(showBackground = true, name = "Notice List Page Preview")
@Composable
fun PreviewNoticeListPage() {
    val dummyNotices = listOf(
        Notice(
            title = "School Closure",
            description = "The school will be closed on 10th May due to a public holiday.",
            date = LocalDate.of(2024, 5, 10),
            fileUrl = "https://example.com/closure.pdf"
        ),
        Notice(
            title = "New Timetable",
            description = "A new timetable will be implemented from next week.",
            date = LocalDate.of(2024, 5, 17),
            fileUrl = "https://example.com/timetable.docx"
        ),
        Notice(
            title = "PTA Meeting",
            description = "PTA meeting scheduled for 15th May at 5 PM.",
            date = LocalDate.of(2024, 5, 15)
        )
    )

    NoticeListPage(

        onReadClick = { },
        onDownloadClick = {  }
    )
}



