package com.samrish.driver.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.services.click

class test_file_trips_detail : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            val painter = painterResource(id = R.drawable.signal)
            design(painter)

        }

    }
}

@Composable
fun design(
    painter: Painter
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)){
        Column {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(PaddingValues(start = 25.dp, top = 30.dp, end = 12.dp, bottom = 20.dp))){
                Text(text = "ABC Transport Co.", style = TextStyle(
                    color = Color.Black,
                    fontSize = 23.sp
                ) )
            }

//            Box(modifier = Modifier.fillMaxSize().background(Color.Gray)){
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(1f),
                    colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(35.dp,35.dp)
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 25.dp, top = 30.dp, end = 12.dp)){
                        Text(text = "CURRENT ASSIGNMENT", style = TextStyle(color = Color.Gray, fontSize = 21.sp, fontWeight = FontWeight.Bold))

                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 25.dp, top = 30.dp, end = 12.dp)){
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "98787878", style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold))
                                Text(text = "25 April 2023", style = TextStyle(color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold))

                            }
                            Text(text = "(BH4-AHL-BND-PNB)", style = TextStyle(color = Color.Gray, fontSize = 17.sp, fontWeight = FontWeight.SemiBold))
                        }
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
//                        .height(160.dp)
                        .padding(start = 25.dp, top = 30.dp, end = 12.dp),
                        contentAlignment= Alignment.Center)
                    {
                        Column(modifier = Modifier.fillMaxWidth()){
                            Box(modifier = Modifier.fillMaxWidth(),contentAlignment= Alignment.Center){
                                Image(painter = painter, contentDescription = null,
                                    Modifier
                                        .height(100.dp)
                                        .fillMaxSize())
                            }

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp),contentAlignment= Alignment.Center){
                                Text(text = "Sharing Location", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium))
                            }
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),contentAlignment= Alignment.Center){
                                Text(text = "IN TRANSIT", style = TextStyle(color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Medium))
                            }
                            Box(modifier = Modifier.fillMaxWidth(),contentAlignment= Alignment.Center){
                                Text(text = "Departed from AHL at 12:30 hrs", style = TextStyle(color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Medium))
                            }

                        }

                    }

                    Surface(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp))
                    {
                        val context = LocalContext.current
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .background(Color.White), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    Color.LightGray
                                ),
                                onClick = {
                                    Toast.makeText(context, "Schedule Selected", Toast.LENGTH_SHORT).show()
                                    Log.i("toast","new") }
                            ) {
                                Text(text = "Schedule", style = TextStyle(color = Color.Black))

                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    Color.LightGray
                                ),
                                onClick = { Toast.makeText(context, "Documents Selected", Toast.LENGTH_SHORT).show()
                                    Log.i("toast","new")}) {
                                Text(text = "Documents",style = TextStyle(color = Color.Black))

                            }

                        }

                    }

                    Box(modifier = Modifier.height(50.dp),contentAlignment = Alignment.Center){
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .background(Color.White), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom
                        ){
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Total Distance Covered", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium))
                                Box(contentAlignment= Alignment.Center){
                                    Text(text = "20 kms", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium))
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Total Travelled Time", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium))
                                Text(text = "2 hours", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium))

                            }
                        }

                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, top = 30.dp, end = 12.dp)
                        .height(90.dp),contentAlignment = Alignment.BottomStart){
                        Column {
                            Text(text = "Next Destination",style = TextStyle(color = Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Bold))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom ) {
                                Text(text = "BH4 - Bharat 4",style = TextStyle(color = Color.Black, fontSize = 17.sp, fontWeight = FontWeight.Bold))
                                Text(text = "STA 09:00 hours",style = TextStyle(color = Color.Gray, fontSize = 13.sp, fontWeight = FontWeight.Medium))

                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom ) {
                                Text(text = "Distance 40kms",style = TextStyle(color = Color.Gray, fontSize = 13.sp, fontWeight = FontWeight.Medium))
                                Text(text = "Estimated Time 09:00 hours",style = TextStyle(color = Color.Gray, fontSize = 13.sp, fontWeight = FontWeight.Medium))

                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom ) {
                                Text(text = "Distance Covered 20kms",style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Medium))
                                Text(text = "Travelled Time 2 hours",style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Medium))

                            }
                        }
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
//                        .fillMaxHeight()
                        .padding(start = 25.dp, top = 30.dp, end = 12.dp, bottom = 30.dp),
                        contentAlignment = Alignment.BottomStart

                    )
                    {
                        val context = LocalContext.current
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(colors = ButtonDefaults.buttonColors(
                                Color.Red
                            ),
                                onClick = {
                                    Toast.makeText(context, "Schedule Selected", Toast.LENGTH_LONG).show()
                                    Log.i("toast","new")
                                }) {
                                Text(text = "Check In ")
                            }
                            Button(colors = ButtonDefaults.buttonColors(
                                Color.Red
                            ),
                                onClick = { /*TODO*/ }) {
                                Text(text = "Cancel ")
                            }
                            Button(colors = ButtonDefaults.buttonColors(
                                Color.Red
                            ),
                                onClick = { click(context) }) {
                                Text(text = "End ")
                            }

                        }
                    }
                }
            }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    val painter = painterResource(id = R.drawable.signal)
    design(painter)

}