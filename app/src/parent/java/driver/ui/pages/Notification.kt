package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun notificationScreen() {
    val gradient = Brush.linearGradient(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.parseColor("#E8F1F8"))
        ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
    )

    val gry = Color(android.graphics.Color.parseColor("#838383"))
    val fontStyle: FontFamily = FontFamily.SansSerif
    val back = Color(android.graphics.Color.parseColor("#F5F5F5"))

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 18.dp)
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.width(30.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .height(25.dp)
                                .clickable {
//                                navController.popBackStack()
                                },
                        )
                    }
                    Text(
                        text = "Notifications",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W600
                        )
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxSize()
            )
            {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 10.dp)) {
                    val fontStyle: FontFamily = FontFamily.SansSerif

                    val gry=Color(android.graphics.Color.parseColor("#838383"))

                    Box(
                        modifier = Modifier
                            .fillMaxSize(1f)
//                            .clickable { onClick(trip) }
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
//                verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = "Driver Assigned",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                                fontFamily = fontStyle,
                                                fontWeight = FontWeight.W700
                                            )
                                        )


                                        Text(
                                            text = "19 Feb 2024, 13:00 pm",
                                            style = TextStyle(
                                                color = gry,
                                                fontSize = 12.sp,
                                                fontFamily = fontStyle,
                                                fontWeight = FontWeight.W600
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Atul was assigned by Atul Thapliyal",
                                            style = TextStyle(
                                                color = gry,
                                                fontSize = 12.sp,
                                                fontFamily = fontStyle,
                                                fontWeight = FontWeight.W400
                                            )
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
//                verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = "Driver Assigned",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                                fontFamily = fontStyle,
                                                fontWeight = FontWeight.W700
                                            )
                                        )


                                        Text(
                                            text = "19 Feb 2024, 13:00 pm",
                                            style = TextStyle(
                                                color = gry,
                                                fontSize = 12.sp,
                                                fontFamily = fontStyle,
                                                fontWeight = FontWeight.W600
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Atul was assigned by Atul Thapliyal",
                                            style = TextStyle(
                                                color = gry,
                                                fontSize = 12.sp,
                                                fontFamily = fontStyle,
                                                fontWeight = FontWeight.W400
                                            )
                                        )
                                    }
                                }
                            }
                                Spacer(modifier = Modifier.height(16.dp))

                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
//                verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Text(
                                                text = "Driver has reached ",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 14.sp,
                                                    fontFamily = fontStyle,
                                                    fontWeight = FontWeight.W700
                                                )
                                            )


                                            Text(
                                                text = "19 Feb 2024, 13:00 pm",
                                                style = TextStyle(
                                                    color = gry,
                                                    fontSize = 12.sp,
                                                    fontFamily = fontStyle,
                                                    fontWeight = FontWeight.W600
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Reached at final Destination",
                                                style = TextStyle(
                                                    color = gry,
                                                    fontSize = 12.sp,
                                                    fontFamily = fontStyle,
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
        }
    }



@Composable
@Preview
fun notificationScreenPreview(){
    notificationScreen()
}