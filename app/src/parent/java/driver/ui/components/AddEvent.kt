package driver.ui.components

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import driver.ui.pages.getByteArrayFromUri
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventPage() {

    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var scope by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var instituteName by remember { mutableStateOf("") }


    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val mTime = remember { mutableStateOf("") }

    val timePickerDialog = android.app.TimePickerDialog(
        context,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val interactTime = remember { MutableInteractionSource() }
    val isTimePressed: Boolean by interactTime.collectIsPressedAsState()


    var selectedCoverImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var selectedImageUris by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val getCoverImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedCoverImageUri = uri

        uri?.let {
            val mimeType = context.contentResolver.getType(uri)
            val byteArray = getByteArrayFromUri(context, it)
//                postUploadViewModel.uploadPosts(byteArray, mimeType)
        }

    }

    val getMultipleEventImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris: List<Uri?> ->
        selectedImageUris = uris
        uris.forEach { uri ->
            uri?.let {
                val mimeType = context.contentResolver.getType(uri)
                val byteArray = getByteArrayFromUri(context, it)
//                postUploadViewModel.uploadPosts(byteArray, mimeType)
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(modifier = Modifier.fillMaxWidth(1f)) {
            Text(
                text = "NEW EVENT REGISTRATION",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {

            OutlinedTextField(
                value = instituteName,
                onValueChange = { instituteName = it },
                label = { Text("Institute Name") },
                placeholder = { Text("Enter Institute name ") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                placeholder = { Text("Enter Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Enter Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = scope,
                onValueChange = { scope = it },
                label = { Text("Scope ") },
                placeholder = { Text("Enter Scope of Event") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            val datePickerDialog =
                DatePickerDialog(
                    context,
                    { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                        val formattedMonth = (month + 1).toString().padStart(2, '0')
                        val formattedDay = dayOfMonth.toString()
                            .padStart(2, '0')

                        selectedDate = "$formattedDay-$formattedMonth-$year"
                    },
                    year,
                    month,
                    day
                )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Date of Event") },
                value = selectedDate,
                onValueChange = {},
                trailingIcon = { Icons.Default.DateRange },
                interactionSource = interactionSource
            )

            if (isPressed) {
                datePickerDialog.show()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Time of Event") },
                value = mTime.value,
                onValueChange = {},
                trailingIcon = { Icons.Default.DateRange },
                interactionSource = interactTime
            )
            if (isTimePressed) {
                timePickerDialog.show()
            }
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Select cover photo", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(
                    onClick = {

                        getCoverImage.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text(
                        "Select Background", style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)) {
                AsyncImage(
                    model = selectedCoverImageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Description photos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                TextButton(
                    onClick = {
                        getMultipleEventImage.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text(
                        "Select Photos", style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            val columns = when (selectedImageUris.size) {
                1 -> 1
                2 -> 2
                3 -> 2
                else -> 2
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(max = 450.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxWidth()

                ) {
                    val imageCount = selectedImageUris.size

                    items(imageCount, key = { it }) { index ->
                        val uri = selectedImageUris[index]
                        if (uri != null) {
                            if (index < 4) {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Selected Image",
                                        modifier = Modifier.fillMaxSize().fillMaxHeight().align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                        if (index == 3) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .zIndex(1f)
                                    .background(Color.Black.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "+${imageCount - (index + 1)}",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(top = 100.dp, bottom = 104.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AddEventPreview() {
    AddEventPage()
}