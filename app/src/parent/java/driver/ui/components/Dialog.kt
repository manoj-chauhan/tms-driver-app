package driver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.node.CanFocusChecker.start
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.drishto.driver.R
import driver.ui.pages.NoticeAll
import driver.ui.pages.NoticeCard
import driver.ui.pages.noticeData
import java.nio.file.WatchEvent




@Composable
fun ProfileDialog(setShowDialog: (Boolean) -> Unit) {
    val customLightGray = Color(android.graphics.Color.parseColor("#F0F0F0"))


    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .width(280.dp)
                )

            {


//                Box(modifier = Modifier
//                    .clip(shape = RoundedCornerShape(10.dp))
//                    .background(Color.LightGray)){}

                Spacer(modifier = Modifier.height(7.dp))

                Text( text = "Profiles",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(customLightGray)
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(bottom = 8.dp)
                        .padding(start = 14.dp)
                        .padding(end = 14.dp)) {
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(bottom = 2.dp), verticalAlignment = Alignment.CenterVertically

                            ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, shape = CircleShape
                                    )
                                    .size(30.dp)

                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.boy),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clip(CircleShape),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Krish Chauhan", textAlign = TextAlign.Center);

                        }
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(bottom = 2.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, shape = CircleShape
                                    )
                                    .size(30.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.boy),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clip(CircleShape),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Krish Chauhan", textAlign = TextAlign.Center);

                        }
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(bottom = 2.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, shape = CircleShape
                                    )
                                    .size(30.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.boy),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clip(CircleShape),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Krish Chauhan", textAlign = TextAlign.Center);

                        }
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(bottom = 2.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.LightGray, shape = CircleShape
                                    )
                                    .align(Alignment.CenterVertically)


                                    .size(30.dp)
                            ) {

                                Icon(
                                    imageVector = Icons.Outlined.PersonAdd,
                                    contentDescription = "Add Account",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Create Profile", textAlign = TextAlign.Center);

                        }


                    }

                }
                Spacer(modifier = Modifier.height(13.dp))
                Column(modifier=Modifier.fillMaxWidth()) {
                    Text( text = "Manage Account",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray)
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        TextButton(onClick = { }) {
                            Text("Terms Of Use", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        TextButton(onClick = { }) {
                            Text("Privacy Policy", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                }


            }
        }
    }
}



//@Preview
//@Composable
//fun DialogPreview(){
//    Dialog(setShowDialog ={true})
//
//
//}

