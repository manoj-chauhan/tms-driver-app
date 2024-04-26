package driver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R
import driver.ui.pages.ImageScrollWithTextOverlay

@Composable
fun CommentPost() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RectangleShape)
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(70.dp)
                            .padding(start = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp)
                                .height(25.dp)
                                .clickable {
//                                    navController.popBackStack()
                                },
                        )

                    }
                    Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                        Text(
                            text = "Post",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(modifier = Modifier) {
                PostContent()
            }

        }
    }
}

@Composable
fun PostContent() {
    val gry = Color(android.graphics.Color.parseColor("#838383"))
    val images = listOf(R.drawable.hi)

    Box(
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Column {
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                    //                verticalArrangement = Arrangement.SpaceBetween
                ) {
    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color.White, shape = CircleShape
                                )
                                .size(44.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.atul),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(200.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 0.dp, Color.White, shape = CircleShape
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
    
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Delhi Public School", style = TextStyle(
                                        fontSize = 16.sp, fontFamily = FontFamily.SansSerif
                                    )
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Sonipath, Haryana", style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color.Gray
                                    )
                                )
                            }
                        }
    
                    }
    
                    Spacer(modifier = Modifier.size(15.dp))
    
                    val images = listOf(
                        "https://picsum.photos/200/300",
                        "https://picsum.photos/200/300",
                        "https://picsum.photos/200/300",
                    )
                    Row(
                        modifier = Modifier
                    ) {
                        ImageScrollWithTextOverlay(images = images)
    
                    }
    
                    Spacer(modifier = Modifier.size(15.dp))
    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(
                                vertical = 0.dp, horizontal = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(
                                text = "50 comments",
                                style = TextStyle(fontSize = 12.sp, color = gry)
                            )
    
                            Spacer(modifier = Modifier.width(12.dp))
    
                            Text(
                                text = "100 likes",
                                style = TextStyle(fontSize = 12.sp, color = gry)
                            )
                        }
    
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.like),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp),
                                contentScale = ContentScale.FillBounds
                            )
    
                            Spacer(modifier = Modifier.width(12.dp))
    
                            Image(
                                painter = painterResource(id = R.drawable.message),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp),
                                contentScale = ContentScale.FillBounds
                            )
    
                            Spacer(modifier = Modifier.width(10.dp))
    
                            Image(
                                painter = painterResource(id = R.drawable.share),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp),
                                contentScale = ContentScale.FillBounds
                            )
    
                        }
                    }
                }
            }
            
            Text(text = "Comments")
            
        }

        Spacer(modifier = Modifier.height(12.dp))

    }
}


@Preview
@Composable
fun CommentPostPreview() {
    CommentPost()
}