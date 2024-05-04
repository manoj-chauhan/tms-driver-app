package driver.NoticeManagement

import driver.network.NoticeNetRepository
import javax.inject.Inject

class NoticeManagerImpl @Inject constructor(
    private val noticeNetRepository: NoticeNetRepository,
): NoticeManager {
    override fun addNotice(
        noticeName: String,
        selectedDate:String,
        description: String,
    ) {
        return noticeNetRepository.addNotice(noticeName, description, selectedDate)
    }
}
