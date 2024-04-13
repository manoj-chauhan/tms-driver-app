package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drishto.driver.R

@Composable
fun pro() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.hi),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
                ) {
                    Text(text = "Krish Chauhan")
                    Text(text = "@Krish Chauhan")

                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "EDUCATION")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 20.dp, bottom = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color.White,
                                    shape = CircleShape
                                )
                                .size(90.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.atul),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(200.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 0.dp,
                                        Color.White,
                                        shape = CircleShape
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Atul")
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun proPreview() {
    pro()
}