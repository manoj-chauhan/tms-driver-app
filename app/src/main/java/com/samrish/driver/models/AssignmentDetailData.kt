package com.samrish.driver.models

data class AssignmentDetailData (
    var tripDetail: TripDetail,
    var activeStatusDetail: ActiveStatusDetail,
    var loc: Schedule,
    var isDataLoaded: Boolean = false
)
