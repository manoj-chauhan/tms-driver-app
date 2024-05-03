package driver.models

import driver.ui.pages.Notice
import java.time.LocalDate

data class Notice_List(
    val title: String,
    val description: String,
    val date: LocalDate,
    val fileUrl: String? = null,
    val fileType: String? = null
)


fun getDummyNotices(): List<Notice> {
    return listOf(
        Notice(
            title = "School Closure",
            description = "The school will be closed due to a national holiday.",
            date = LocalDate.of(2024, 5, 5),
            fileUrl = "https://www.clickdimensions.com/links/TestPDFfile.pdf",
            fileType = "PDF"
        ),
        Notice(
            title = "New Timetable",
            description = "A new timetable will be implemented from the next term.",
            date = LocalDate.of(2024, 6, 10),
            fileUrl = "https://www.clickdimensions.com/links/TestPDFfile.pdf",
            fileType = "PDF"
        ),
        Notice(
            title = "PTA Meeting",
            description = "PTA meeting scheduled for 15 May 2024 at 5 PM.",
            date = LocalDate.of(2024, 5, 15)
        )
    )
}