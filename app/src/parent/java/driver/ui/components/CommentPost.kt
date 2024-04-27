package driver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R
import driver.models.PostsFeed
import driver.ui.pages.ImageScrollWithTextOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentPost(postsFeed: PostsFeed?) {
    
    var comment by remember {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column (modifier = Modifier, verticalArrangement = Arrangement.SpaceBetween){
            Box(modifier = Modifier.fillMaxHeight(0.95f)) {
                LazyColumn(modifier = Modifier) {
                    item {
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
                                PostContent(postsFeed)
                            }
                        }
                    }
                }
            }


            Row(modifier = Modifier
                .fillMaxWidth().padding(horizontal = 10.dp)
                .background(Color.White), horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                BasicTextField(value = comment,
                    onValueChange = { comment = it },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .height(35.dp).fillMaxWidth(0.8f),

                    enabled = true,
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = comment,
                            innerTextField = innerTextField,
                            visualTransformation = VisualTransformation.None,
                            trailingIcon = { /* ... */ },
                            placeholder = {
                                Text(
                                    text = "Type your comment",
                                    fontSize = 14.sp,
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.LightGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            enabled = true,
                            interactionSource = interactionSource,
                            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                                top = 0.dp, bottom = 0.dp
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )
                    }

                )

                Spacer(modifier = Modifier.width(7.dp))

                Button(
                    modifier = Modifier
                        .height(35.dp).width(100.dp)
                        .align(Alignment.Bottom),
                    enabled = true,
                    onClick = {

                    },
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    val primary = Color(0xFF92A3FD)
                    val secondary = Color(0XFF9DCEFF)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(35.dp)
                            .align(Alignment.Bottom)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        primary,
                                        secondary
                                    )
                                ),
                                shape = RoundedCornerShape(5.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Send",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun PostContent(postsFeed: PostsFeed?) {
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

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)

                    ){
                        ExpandableText("The event was really enjoying. We had talked about a lot of changes that can be made within an organization to make it grow a bit larger. The event was really enjoying. We had talked about a lot of changes that can be made within an organization to make it grow a bit larger.The event was really enjoying. We had talked about a lot of changes that can be made within an organization to make it grow a bit larger.The event was really enjoying. We had talked about a lot of changes that can be made within an organization to make it grow a bit larger.The event was really enjoying. We had talked about a lot of changes that can be made within an organization to make it grow a bit larger.The event was really enjoying. We had talked about a lot of changes that can be made within an organization to make it grow a bit larger.")
                    }
                    Spacer(modifier = Modifier.size(15.dp))


                    val images = listOf(
                        "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",
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
                                text = "${postsFeed?.comments} comments",
                                style = TextStyle(fontSize = 12.sp, color = gry)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "${postsFeed?.likes} likes",
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
            HorizontalDivider(
                modifier = Modifier
                    .height(2.dp)
                    .padding(
                        vertical = 0.dp, horizontal = 10.dp
                    )
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(12.dp))

            CommentsArea()
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun CommentsArea() {
    Box(
        modifier = Modifier
            .padding(vertical = 0.dp, horizontal = 10.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Comments",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W500)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    CommentsInPost()

                }

            }

        }

    }
}

@Composable
fun CommentsInPost() {

    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.width(33.dp)) {
            Box(
                modifier = Modifier
                    .background(
                        Color.White, shape = CircleShape
                    )
                    .size(35.dp)
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
        }
        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(6.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxWidth()
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
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

                Spacer(modifier = Modifier.height(10.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "The event was really enjoying. We had talked about a lot of changes that can be made within an organization to make it grow a bit larger.",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(
                    onClick = {
                    }
                ) {
                    Text(
                        "Like", style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                VerticalDivider(modifier = Modifier
                    .width(2.dp)
                    .height(14.dp)
                    .background(Color.LightGray))

                TextButton(
                    onClick = {
                    }
                ) {
                    Text(
                        "Reply", style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableText(
    text: String,
    maxLines: Int = 4,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            expanded = !expanded
                        }
                    )
                }
                .background(Color.Transparent)
        )

        if (!expanded && text.countLines() > maxLines) {
            Text(
                text = "Show more",
                style = TextStyle(color = Color.LightGray),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
//                    .clickable { expanded = true }
                    .padding(0.dp)
            )
        }
    }
}

fun String.countLines(): Int {
    return this.split("\n").size
}