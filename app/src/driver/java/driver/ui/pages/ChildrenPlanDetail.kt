package driver.ui.pages

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drishto.driver.models.ChildrenList
import com.drishto.driver.models.TripSchedulesList
import com.drishto.driver.ui.pages.AgeDisplay
import driver.ui.viewmodels.DriverPlanDetailsViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun ChildrenPlanDetail(
    operatorId: Int, planId: Int,planCode:String, navHostController: NavHostController, onStudentSelected: (assignment: ChildrenList) -> Unit, activity: ComponentActivity,
    ch: DriverPlanDetailsViewModel = hiltViewModel()) {
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val childrens by ch.childrenList.collectAsStateWithLifecycle()
    ch.getChildrenList(context = LocalContext.current, operatorId, planId)

    val context = LocalContext.current

    val schedules by ch.planList.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        ch.fetchSchedule(context = context, operatorId, planCode)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            PaddingValues(
                                end = 12.dp,
                            )
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    schedules?.companyName?.let {
                        Text(
                            text = it, style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
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
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Schedules List", style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    schedules?.tripPlanScheduleList?.forEachIndexed { index, schedule ->
                        schedulesList(
                            schedule, index, schedules?.tripPlanScheduleList!!,
                            schedules!!.startTime
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Childrens List", style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Button(
                            modifier = Modifier
                                .height(25.dp)
                                .align(Alignment.Bottom),
                            enabled = true,
                            onClick = {
                                navHostController.navigate("student-addition/$operatorId/$planCode")
                            },
                            contentPadding = PaddingValues(),
                            colors = ButtonDefaults.buttonColors(
                                Color.Transparent
                            ),
                            shape = RoundedCornerShape(40.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .heightIn(35.dp)
                                    .width(100.dp)
                                    .align(Alignment.Bottom)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(40.dp)
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Add Student",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    childrens?.forEach { children ->
                        ChildrensList(children, onStudentSelected)
                    }
                }
            }
        }
    }

}

@Composable
fun schedulesList(
    schedule: TripSchedulesList,
    index: Int,
    schedules: List<TripSchedulesList>,
    startTime: String
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val newformatter = DateTimeFormatter.ofPattern("HH:mm")


    Log.d("TAG", "schedulesList:$startTime ")


    if (index == 0) {
        schedule.departure = LocalTime.parse(startTime, formatter)
            .plusMinutes(schedule.haltTime.toLong())
            .format(newformatter)
    } else {
        val previousSchedule = schedules[index - 1]
        val travelTime = previousSchedule.travelTime
        val arrivalTime = LocalTime.parse(previousSchedule.departure, newformatter)
            .plusMinutes(travelTime.toLong())
        schedule.arrival = arrivalTime.toString()
        schedule.departure = schedule.arrival.let {
            LocalTime.parse(it, newformatter).plusMinutes(schedule.haltTime.toLong()).toString()
        }
    }

    Spacer(modifier = Modifier.padding(8.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "${schedule.placeCode} - ", style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {

                Text(
                    text = "${schedule.placeName}", style = TextStyle(
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.width(50.dp)) {
                Text(
                    text = "  ", style = TextStyle(
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Box(modifier = Modifier.width(100.dp), Alignment.Center) {

                Text(
                    text = "Arrival", style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Box(modifier = Modifier.width(100.dp), Alignment.Center) {

                Text(
                    text = "Departure", style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.width(50.dp)) {

                Text(
                    text = "Planned", style = TextStyle(
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            if (index == 0) {
                Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                    Text(
                        text = "--", style = TextStyle(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

            } else {

                Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                    schedule.arrival?.let {
                        Text(
                            text = it, style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            if (index == (schedules.size - 1)) {
                Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                    Text(
                        text = "--", style = TextStyle(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            } else {
                Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                    schedule.departure?.let {
                        Text(
                            text = it, style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

        }
    }
}

fun calculateAge(dateOfBirth: String): Number {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val birthDate = LocalDate.parse(dateOfBirth, dateFormatter)

    val currentDate = LocalDate.now()

    val age = ChronoUnit.YEARS.between(birthDate, currentDate).toInt()

    return age
}

@Composable
fun AgeDisplay(dateOfBirth: String): Number {
    val age = calculateAge(dateOfBirth)
    return age
}

@Composable
fun ChildrensList(children: ChildrenList,  onClick: (children: ChildrenList) -> Unit) {

    var age: Number = 0
    children.dateOfBirth.let {
        age = AgeDisplay(dateOfBirth = it)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick(children) }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${children.name} s/o ${children.guardianName} ",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W600
                    )
                )

                Text(
                    text = "$age yrs",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W400
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = children.schoolName,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W400
                    )
                )

                Text(
                    text = "${children.standard} Std",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W400
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Boarding Place",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W400
                    )
                )

                Text(
                    text = children.boardingPlaceName,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W400
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DeBoarding Place",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W400
                    )
                )

                children.deBoardingPlaceName?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.W400
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Phone Number - ${children.primaryPhoneNumber} ",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W400
                    )
                )


                if(children.secondaryPhoneNumber != null) {
                    if (children.secondaryPhoneNumber?.length!! > 0) {
                        children.secondaryPhoneNumber?.let {
                            Text(
                                text = ",$it",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}