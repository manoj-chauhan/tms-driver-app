package com.samrish.driver.models

data class AssignmentDetailData (
    var tripDetail: TripDetail,
    var activeStatusDetail: ActiveStatusDetail,
    var loc: Schedule,
    var documents: MutableList<Documents>?,
    var isDataLoaded: Boolean = false
)

data class PastAssignmentDetailData (
    var tripDetail: TripDetail,
    var documents: MutableList<Documents>?,
    var isDataLoaded: Boolean = false
)
