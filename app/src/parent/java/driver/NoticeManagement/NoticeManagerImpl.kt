package driver.NoticeManagement

import driver.instituteManagement.InstitueManager
import driver.models.ContactList
import driver.network.InstituteNetRepository
import driver.network.NoticeNetRepository
import javax.inject.Inject

class NoticeManagerImpl @Inject constructor(
    private val AddNoticeNetRepository: NoticeNetRepository,
): NoticeManager {
    override fun addNotice(
        noticeName: String,
        selectedDate:String,
        description: String,




    ) {
//        return NoticeNetRepository.addNotice


    }
}
