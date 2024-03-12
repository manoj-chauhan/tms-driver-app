package driver.ui.pages

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import driver.ui.viewmodels.FeedbackVIewModel

@Composable
fun notificationScreen(idUser:Int,navHostController: NavHostController) {
    val gradient = Brush.linearGradient(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.parseColor("#E8F1F8"))
        ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
    )

    val fontStyle: FontFamily = FontFamily.SansSerif
    val primary = Color(0xFF92A3FD)
    val secondary = Color(0XFF9DCEFF)

    val fd:FeedbackVIewModel = hiltViewModel()

    var message by remember { mutableStateOf("") }

    Log.d("TAG", "notificationScreen: $idUser")

    Box(
        modifier = Modifier.fillMaxSize()
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
                        modifier = Modifier.width(30.dp), horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .height(25.dp)
                                .clickable {
                                    navHostController.popBackStack()
                                },
                        )
                    }
                    Text(
                        text = "Feedback Form", style = TextStyle(
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
            ) {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 10.dp)) {
                    val fontStyle: FontFamily = FontFamily.SansSerif

                    val gry = Color(android.graphics.Color.parseColor("#838383"))

                    Box(
                        modifier = Modifier.fillMaxSize(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White,
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(1f)
                                        .padding(10.dp), verticalArrangement = Arrangement.Center
                                ) {

                                    OutlinedTextField(
                                        value = message,
                                        onValueChange = { message = it },
                                        label = { Text("Message") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(220.dp)
                                            .padding(bottom = 16.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        onClick = {
                                            fd.sendFeedback(idUser, message)
                                            navHostController.popBackStack()

                                        },
                                        enabled = if (message.isNotEmpty()) {
                                            true
                                        } else {
                                            false
                                        },
                                        contentPadding = PaddingValues(),
                                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(40.dp)
                                                .background(
                                                    brush = Brush.horizontalGradient(
                                                        listOf(
                                                            primary,
                                                            secondary
                                                        )
                                                    ), shape = RoundedCornerShape(40.dp)
                                                ),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(text = "Send")
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
