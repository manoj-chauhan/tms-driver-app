package driver.NoticeManagement

import driver.models.Notice_List
import driver.network.NoticeNetRepository
import javax.inject.Inject

class NoticeManagerImpl @Inject constructor(
    private val noticeNetRepository: NoticeNetRepository,
): NoticeManager {
    override suspend fun getAllNotices(): List<Notice_List>? {
        return noticeNetRepository.getAllNotices()
    }

    override suspend fun addNotice(
        noticeName: String,
        selectedDate:String,
        description: String,
    ) {
        return noticeNetRepository.addNotice(noticeName, description, selectedDate)
    }
}
