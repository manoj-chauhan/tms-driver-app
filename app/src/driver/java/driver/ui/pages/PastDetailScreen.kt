package com.samrish.driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.ui.components.DocumentsDialog
import driver.ui.viewmodels.PastAssignmentDetailViewModel
import driver.ui.pages.History
import java.text.SimpleDateFormat

@Composable
fun PastAssignmentDetailScreen(
    navController: NavHostController,
    operatorId: Int,
    tripId: Int,
    tripCode: String,
    pt: PastAssignmentDetailViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val pastAssignment by pt.pastassignmentDetail.collectAsStateWithLifecycle()

    val isDocumentSelected = remember { mutableStateOf(true); }
    val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
    val outputFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm")


    if (pastAssignment?.isDataLoaded != true) {
        pt.fetchAssignmentDetail(
            context = context,
            tripId = tripId,
            tripCode = tripCode,
            operatorId = operatorId
        )
    }

    pastAssignment?.let {
        val parsedDate = remember(it.tripDetail.tripDateTime) {
                it.tripDetail.tripDateTime.let { it1 ->
                    inputFormat.parse(
                        it1
                    )
                }
            }
        val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

        val annotatedString = remember {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append(it?.tripDetail?.tripCode)
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                ) {
                    append("  $formattedDate")
                }
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()) // Use verticalScroll instead of ScrollView
                .fillMaxWidth()
                .fillMaxHeight() // You can adjust these modifiers as needed
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(35.dp, 35.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(start = 25.dp, top = 30.dp, end = 12.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = annotatedString,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "Departed from AHL at 12:30 hr ",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )

                            it.tripDetail?.status?.let { it1 ->
                                Text(
                                    text = it1,
                                    style = TextStyle(
                                        color = Color.Red,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                        }
                    }
                }

                if (isDocumentSelected.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 25.dp,
                                top = 10.dp,
                                end = 12.dp,
                                bottom = 20.dp
                            ),
                        contentAlignment = Alignment.Center
                    )
                    {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.LightGray,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            pastAssignment?.documents.let { document ->
                                if (document != null) {
                                    DocumentsDialog(operatorId, document)
                                }
                            }
                        }
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)) {
                    History(
                        navController = navController,
                        it.tripDetail.tripCode,
                        it.tripDetail.operatorId
                    )
                }
            }
        }

    }

}