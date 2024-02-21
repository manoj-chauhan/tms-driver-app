package driver.ui.pages

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = Color(0xFF92A3FD),
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { androidx.compose.animation.core.Animatable(initialValue = 0f) },
        remember { androidx.compose.animation.core.Animatable(initialValue = 0f) },
        remember { androidx.compose.animation.core.Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEach { value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    )
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(
    cornerRadius: Dp = 16.dp,
    progressIndicatorColor: Color = Color(0xFF35898f),
    progressIndicatorSize: Dp = 50.dp
) {
    AlertDialog(
        onDismissRequest = {
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier
                .padding(start = 42.dp, end = 42.dp)
                .background(color = Color.White, shape = RoundedCornerShape(cornerRadius))
                .padding(top = 36.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProgressIndicatorLoading(
                progressIndicatorSize = progressIndicatorSize,
                progressIndicatorColor = progressIndicatorColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Please wait...",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Composable
private fun ProgressIndicatorLoading(
    progressIndicatorSize: Dp,
    progressIndicatorColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
            }
        ), label = ""
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                8.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White,
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White
    )
}
