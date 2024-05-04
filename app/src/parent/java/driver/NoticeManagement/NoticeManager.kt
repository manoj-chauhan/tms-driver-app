package driver.NoticeManagement

interface NoticeManager {
    fun addNotice(
        instituteName: String,
        description: String,
        selectedDate:String,
    );
}