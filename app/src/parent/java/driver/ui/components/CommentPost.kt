package driver.ui.components

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Share
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drishto.driver.R
import driver.models.CommentPost
import driver.models.PostsFeed
import driver.ui.pages.ImageScrollWithTextOverlay
import driver.ui.viewmodels.PostActionsViewModel
import driver.ui.viewmodels.PostsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentPost(navController: NavHostController, postId: String?) {
    var comment by remember {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }
    val pa: PostsViewModel = hiltViewModel()
    val postActions: PostActionsViewModel = hiltViewModel()

    val postsFeed by pa.postDetail.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (postId != null) {
            pa.getPostDetails(postId)
        }
    }

            Log.d("TAG", "CommentPost: $postsFeed")
    val postComments by pa.postComments.collectAsStateWithLifecycle()
    LaunchedEffect(postsFeed) {
        if (postId != null) {
            pa.getPostComments(postId)
        }
    }
    val colortext= Color(android.graphics.Color.parseColor("#1c1b1f"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier, verticalArrangement = Arrangement.SpaceBetween) {
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
                                                    navController.popBackStack()
                                                },
                                        )

                                    }
                                    Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                                        Text(
                                            text = "Post",
                                            style = TextStyle(
                                                color = colortext,
                                                fontSize = 20.sp,
                                                fontFamily = FontFamily.SansSerif,
                                                fontWeight = FontWeight.W500,
                                            )
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Column(modifier = Modifier) {
                                if(postsFeed!=null) {
                                    PostContent(postsFeed, postComments, navController)
                                }
                            }
                        }
                    }
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                BasicTextField(value = comment,
                    onValueChange = { comment = it },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth(0.8f),

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
                                containerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = false,
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
                        .height(35.dp)
                        .width(100.dp)
                        .align(Alignment.Bottom),
                    enabled = true,
                    onClick = {
                        postsFeed?.let { postActions.uploadComments(it.id, comment) }
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
fun PostContent(postsFeed: PostsFeed?, postComments: List<CommentPost>?,
                navController: NavHostController) {
    val colortext= Color(android.graphics.Color.parseColor("#1c1b1f"))

    val postActionViewModel: PostActionsViewModel = hiltViewModel()
    Log.d("Post FEded", "PostContent: $postsFeed")
    var likesCount by remember { mutableIntStateOf(postsFeed?.likes ?: 0) }
    var isLiked by remember { mutableStateOf(postsFeed?.likedStatus) }

    var showDialog = remember { mutableStateOf(false) }

    val mediaUrls = postsFeed?.media?.map { it.mediaUrl } ?: emptyList()

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
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
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
                                    .clickable {

                                        navController.navigate("mediaViewerDialog/${postsFeed?.id}")
                                    }
                                    .border(
                                        width = 0.dp, Color.White, shape = CircleShape
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                if (postsFeed != null) {
                                    Text(
                                        text = postsFeed.userName, style = TextStyle(
                                            fontSize = 16.sp, fontFamily = FontFamily.SansSerif,
                                            color = colortext
                                    )
                                    )
                                }
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

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)

                    ) {
                        ExpandableText("${postsFeed?.message}")
                    }
                    Spacer(modifier = Modifier.size(15.dp))


                    val images = postsFeed?.media?.let {
                        it.map { it.mediaUrl }.toList()
                    }
                    Row(
                        modifier = Modifier
                            .clickable { showDialog.value=true }

                    ) {
                        if (images != null) {
                            ImageScrollWithTextOverlay(images = images)
                        }

                    }

                    Spacer(modifier = Modifier.size(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(0.75f)) {
                            Text(
                                text = "${postsFeed?.comments} comments",
                                style = TextStyle(fontSize = 14.sp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "$likesCount likes",
                                style = TextStyle(fontSize = 14.sp)
                            )
                        }


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val transition = updateTransition(targetState = isLiked)
                            val scale by transition.animateFloat(
                                transitionSpec = {
                                    keyframes {
                                        1.6f at 0
                                        3.0f at 300
                                        1.0f at 200
                                    }
                                }, label = ""
                            ) { liked ->
                                if (liked == true) 1.0f else 0.8f
                            }

                            if (isLiked == true) {
                                Image(
                                    painter = painterResource(id = R.drawable.likenew),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clickable {
                                            postsFeed?.let { postActionViewModel.dislikePost(it.id) }
                                            likesCount--
                                            isLiked = false
                                        }
                                        .scale(scale),
                                    contentScale = ContentScale.FillBounds
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.like),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clickable {
                                            if (postsFeed != null) {
                                                postActionViewModel.likePost(postsFeed.id)
                                            }
                                            likesCount++
                                            isLiked = true
                                        }
                                        .scale(scale),
                                    contentScale = ContentScale.FillBounds
                                )
                            }

                            Icon(
                                imageVector = Icons.Outlined.Message,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
//                                        onCommentClick(post)
                                    }
                            )

                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
//                                        onCommentClick(post)
                                    }
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

            CommentsArea(postComments)
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (showDialog.value) {
            if (postsFeed != null) {
                MediaViewerDialog(
                    post = postsFeed,
                    images = mediaUrls,
                    setShowDialog = { showDialog.value = it }
                )
            }
        }
    }
}

@Composable
fun CommentsArea(postComments: List<CommentPost>?) {
    val colortext= Color(android.graphics.Color.parseColor("#1c1b1f"))


    Box(
        modifier = Modifier
            .padding(vertical = 0.dp, horizontal = 10.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Comments",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W500, color = colortext)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    if (postComments?.size != 0) {
                        postComments?.forEach {
                            CommentsInPost(it)
                        }
                    } else {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "No comments in this post",
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W500)
                            )
                        }
                    }
                }

            }

        }

    }
}

@Composable
fun CommentsInPost(commentPost: CommentPost) {
    val colortext= Color(android.graphics.Color.parseColor("#1c1b1f"))

    Column {
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
                                    text = commentPost.commentedBy, style = TextStyle(
                                        fontSize = 16.sp, fontFamily = FontFamily.SansSerif, color = colortext
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
                            text = commentPost.content,
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