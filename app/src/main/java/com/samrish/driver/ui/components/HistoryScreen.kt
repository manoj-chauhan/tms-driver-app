package com.samrish.driver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(
                        PaddingValues(
                            start = 25.dp, top = 30.dp, end = 12.dp, bottom = 20.dp
                        )
                    )
            ) {

                Text(
                    text = "Old Assignments", style = TextStyle(
                        color = Color.Black, fontSize = 23.sp, fontWeight = FontWeight.Bold
                    )
                )

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(35.dp, 35.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 16.dp, top = 30.dp, end = 12.dp)
                ) {
//                    Text(
//                        text = "CURRENT ASSIGNMENT", style = TextStyle(
//                            color = Color.Gray, fontSize = 21.sp, fontWeight = FontWeight.Bold
//                        )
//                    )
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(16.dp, 16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "2222",
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            )

                            Text(
                                text = "27 Apr 2023 9:00 ",
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "PHL-DHL-BHI", style = TextStyle(fontWeight = FontWeight.SemiBold, color = Color.Gray, fontSize = 14.sp))
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                            Text(text = "Atul Transport Coorporation", style = TextStyle(fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 12.sp))
                            Text(text = "27 Apr 09:30 - 27 Apr 09:35", style = TextStyle(fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 10.sp))
                        }
                    }


                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(16.dp, 16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "2222",
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            )

                            Text(
                                text = "27 Apr 2023 9:00 ",
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "PHL-DHL-BHI", style = TextStyle(fontWeight = FontWeight.SemiBold, color = Color.Gray, fontSize = 14.sp))
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                            Text(text = "Atul Transport Coorporation", style = TextStyle(fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 12.sp))
                            Text(text = "27 Apr 09:30 - 27 Apr 09:35", style = TextStyle(fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 10.sp))
                        }
                    }


                }
            }
        }
    }
}


@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreen()
}
