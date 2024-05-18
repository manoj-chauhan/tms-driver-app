package driver.NoticeManagement

import driver.models.Event
import driver.models.Notice_List

interface NoticeManager {

    suspend fun getNoticeById(noticeId:String): Notice_List?

    suspend fun getAllNotices():List<Notice_List>?


    suspend fun addNotice(
        instituteName: String,
        description: String,
        selectedDate:String,
    );
}