package driver

import kotlinx.serialization.Serializable


@Serializable
sealed class Destination{
    @Serializable
    data class CurrentAssignmentDetail(
        val selectedAssignmentCode: String,
        val passengerTripId: Int,
        val operatorId: Int
    ) : Destination()

    @Serializable
    data class PastAssignmentDetail(
        val selectedAssignmentCode: String,
        val passengerTripId: Int
    ) : Destination()

    @Serializable
    object UserProfile : Destination()

    @Serializable
    object NewHomeScreen : Destination()

    @Serializable
    object Login : Destination()

    @Serializable
    object ProfilesList : Destination()

    @Serializable
    object AddProfile

    @Serializable
    object AddEventForm : Destination()

    @Serializable
    data class EventDetails(val eventId: String) : Destination()

    @Serializable
    object SchoolProfile : Destination()

    @Serializable
    object NoticeLists : Destination()

    @Serializable
    object AddNoticeForm : Destination()



    @Serializable
    object PostPage : Destination()

    @Serializable
    object UserProfileScreen : Destination()

    @Serializable
    object HomeScreen : Destination()

    @Serializable
    data class MapScreen(
        val passengerTripId: Int,
        val tripCode: String
    ) : Destination()

    @Serializable
    object PastTripsList : Destination()

    @Serializable
    data class AddComment(
        val postId: String
    ) : Destination()

    @Serializable
    data class Notification(val userId: Int) : Destination()









}







