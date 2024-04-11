package driver.ui.pages

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    onProfileClick: (Int) -> Unit,
    onLikeClick: (String) -> Unit,
    onCommentClick: (String) -> Unit,
    isDetailScreen: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 0.7f)
            .background(color = Gray)
            .clickable {  }
    ) {
        PostHeader(
            name = "Atul",
            profileUrl = "post.authorImage",
            date = "12/03/2024",
            onProfileClick = {

            }
        )

        PostLikesRow(
            likesCount = 22,
            commentCount = 322,
            onLikeClick = {  },
            onCommentClick = { }
        )

        Text(
            text = "post.text",
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(horizontal = 12.dp),
            maxLines = if (isDetailScreen) 10 else 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun PostHeader(
    modifier: Modifier = Modifier,
    name: String,
    profileUrl: String,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 23.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(23.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Color.White,
                    shape = CircleShape
                )
                .size(44.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.atul),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(
                        width = 0.dp,
                        Color.White,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.FillBounds
            )
        }

        Text(
            text = name,
            color = Color.White
        )

        Box(
            modifier = modifier
                .size(4.dp)
                .clip(CircleShape)

        )

        Text(
            text = date,
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
            ),
            modifier = modifier.weight(1f)
        )

//        Icon(
//            painter = painterResource(id = R.drawable.round_more_horizontal),
//            contentDescription = null,
//            tint = if (true) {
//                LightGray
//            } else {
//                DarkGray
//            }
//        )
    }
}


@Composable
fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentCount: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 0.dp,
                horizontal = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLikeClick
        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.like_icon_outlined),
//                contentDescription = null,
//            )
        }

        Text(
            text = "$likesCount",
            style = TextStyle(fontSize = 18.sp)
        )

        Spacer(modifier = modifier.width(12.dp))

        IconButton(
            onClick = onCommentClick
        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.chat_icon_outlined),
//                contentDescription = null,
//            )
        }

        Text(
            text = "$commentCount",
            style = TextStyle(fontSize = 18.sp)
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostListItemPreview() {

            PostListItem(
                onProfileClick = {},
                onCommentClick = {},
                onLikeClick = {}
            )

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostHeaderPreview() {

            PostHeader(
                name = "Mr Dip",
                profileUrl = "",
                date = "20 min",
                onProfileClick = {}
            )
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostLikesRowPreview() {

            PostLikesRow(
                likesCount = 12,
                commentCount = 2,
                onLikeClick = {},
                onCommentClick = {}
            )

}