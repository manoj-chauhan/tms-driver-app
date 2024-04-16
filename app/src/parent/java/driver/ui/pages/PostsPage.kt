package driver.ui.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PostItem() {

    val primary = Color(0xFF92A3FD)
    val secondary = Color(0XFF9DCEFF)

    var text by remember {
        mutableStateOf("")
    }

    var selectedImageUri by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val getMultipleImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { selectedImageUri = it }
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(modifier = Modifier.width(40.dp), onClick = {

                }) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                }


                Button(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = {
                    },
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
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
                        Text(text = "Post")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {

                val colors = TextFieldDefaults.textFieldColors()
                val interactionSource = remember { MutableInteractionSource() }

                BasicTextField(value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .indicatorLine(
                            enabled = true,
                            isError = false,
                            interactionSource = interactionSource,
                            colors = colors,
                            focusedIndicatorLineThickness = 0.dp,
                            unfocusedIndicatorLineThickness = 0.dp
                        )
//                        .height(140.dp)
                        .fillMaxWidth(0.96f)
                        .background(Color.Transparent),

                    enabled = true,
                    singleLine = false,
                    decorationBox = { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = text,
                            innerTextField = innerTextField,
                            visualTransformation = VisualTransformation.None,
                            trailingIcon = { /* ... */ },
                            placeholder = {
                                Text(
                                    text = "Type the caption !!",
                                    fontSize = 14.sp,
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = false,
                            enabled = true,
                            interactionSource = interactionSource,
                            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                                top = 0.dp, bottom = 0.dp
                            ),
                        )
                    }
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(modifier = Modifier.width(40.dp), onClick = {
                        selectedImageUri
                    }) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null
                        )
                    }

                }

                LazyColumn {
                    items(selectedImageUri){selectedImageUri ->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)), model = selectedImageUri, contentDescription =null
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(modifier = Modifier.width(40.dp), onClick = {
                    getMultipleImage.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                    )
                }) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Filled.Photo,
                        contentDescription = null
                    )
                }
            }
        }
    }

}


@Composable
@Preview
fun PostItemPreview() {
    PostItem()
}

