package com.samrish.driver.ui.composition

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import getTripDetail

@Composable
fun AssignmentDetail(
    assignmentCode:String
) {
    getTripDetail(LocalContext.current, assignmentCode);
    Text(text = "AssignmentDetail $assignmentCode")
}