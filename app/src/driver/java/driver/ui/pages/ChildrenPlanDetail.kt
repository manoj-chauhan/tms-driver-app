package driver.ui.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import driver.ui.viewmodels.DriverPlanDetailsViewModel

@Composable
fun ChildrenPlanDetail() {
    val ch : DriverPlanDetailsViewModel = hiltViewModel()
    val childrens by ch.childrenList.collectAsStateWithLifecycle()
    ch.fetchParentTrip(context = LocalContext.current)

    Log.d("TAG", "ChildrenPlanDetail: $childrens")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
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

                    Text(
                        text = "Mother Divine Public School", style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    )
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

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "SPS - ", style = TextStyle(
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
                                    text = "Samrish Technology Pvt. Ltd.", style = TextStyle(
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

                            Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                                Text(
                                    text = "17-08-2002", style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                                Text(
                                    text = "15-9-1223", style = TextStyle(
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
                                    text = "Actual", style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier.width(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "12-04-2003",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier.width(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "12-9-2023",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "SPS - ", style = TextStyle(
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
                                    text = "Samrish Technology Pvt. Ltd.", style = TextStyle(
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

                            Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                                Text(
                                    text = "17-08-2002", style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                                Text(
                                    text = "15-9-1223", style = TextStyle(
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
                                    text = "Actual", style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier.width(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "12-04-2003",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier.width(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "12-9-2023",
                                    style = TextStyle(
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
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
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
                                    text = "Atul Thapliyal s/o Mr. Dinesh Prasad ",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.W600
                                    )
                                )

                                Text(
                                    text = "22 yrs",
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
                                    text = "Mother Divine Public School",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.W400
                                    )
                                )

                                Text(
                                    text = "7th Std",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.W400
                                    )
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
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
                                    text = "Atul Thapliyal s/o Mr. Dinesh Prasad ",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.W600
                                    )
                                )

                                Text(
                                    text = "22 yrs",
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
                                    text = "Mother Divine Public School",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.W400
                                    )
                                )

                                Text(
                                    text = "7th Std",
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
    }

}



@Preview
@Composable
fun ChildrenPreview() {
    ChildrenPlanDetail()
}