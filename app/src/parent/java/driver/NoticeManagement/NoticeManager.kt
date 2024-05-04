package driver.NoticeManagement

import driver.models.ContactList

interface NoticeManager {





    fun addNotice(
        instituteName: String,
        description: String,
        selectedDate:String,
    );
}