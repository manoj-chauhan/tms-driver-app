package driver.ui.components

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import driver.models.ImagesInfo
import driver.ui.viewmodels.EventsViewModel
import driver.ui.viewmodels.NoticesViewModel
import java.io.ByteArrayOutputStream
import java.util.Calendar
import java.util.Date

@Composable
fun AddNoticeEvent() {

    val fontFamily = FontFamily.SansSerif
    val context = LocalContext.current

    var noticeName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedNoticeUri by remember { mutableStateOf<Uri?>(null) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()



    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val eventsViewModel: EventsViewModel = hiltViewModel()
    val noticesViewModel: NoticesViewModel = hiltViewModel()
    val noticeMedia by eventsViewModel.postDetails.collectAsStateWithLifecycle()
    var notice = remember { mutableStateOf<ImagesInfo?>(null) }

    LaunchedEffect(noticeMedia) {
        if (noticeMedia!=null) {
            notice.value= ImagesInfo(
                type = when (noticeMedia!!.second) {
                    "video/mp4" -> "Video"
                    "image/jpeg", "image/png" -> "Image"
                    "pdf/pdf"->"Pdf"
                    else -> "Unknown"
                },
                mediaId = noticeMedia!!.first,
                caption = "Command 1"
            )

        }
    }

    val getNoticeImage = rememberLauncherForActivityResult(
                ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedNoticeUri = uri

        uri?.let {
            val mimeType = context.contentResolver.getType(uri)
            val byteArray = convertUriToByteArray(context, it)
            eventsViewModel.uploadPosts(byteArray, mimeType)
        }
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, dayOfMonth: Int ->
            selectedDate = "$dayOfMonth-${selectedMonth + 1}-$selectedYear"
        },
        year,
        month,
        day
    )

    val eventViewModel:EventsViewModel= hiltViewModel()
    val noticeCoverPhoto by eventViewModel.postDetails.collectAsStateWithLifecycle()
    var noticeImage = remember { mutableStateOf<ImagesInfo?>(null) }

    LaunchedEffect(noticeCoverPhoto) {
//        coverImage=null

        if (noticeCoverPhoto!=null) {
            noticeImage.value= ImagesInfo(
                type = when (noticeCoverPhoto!!.second) {
                    "video/mp4" -> "Video"
                    "image/jpeg", "image/png" -> "Image"
                    else -> "Unknown"
                },
                mediaId = noticeCoverPhoto!!.first,
                caption = "Command 1"
            )

        }
        Log.d("mediaPosts", "Notice photo: ${noticeImage.value}")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        item {
            Text("Add Notice", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = noticeName,
                onValueChange = { noticeName = it },
                label = { Text("Heading for the Notice") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { getNoticeImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Cover Image")
            }

            Spacer(modifier = Modifier.height(8.dp))

            selectedNoticeUri?.let { uri ->
                DisplaySelectedImage(context, uri)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Date of Event") },
                value = selectedDate,
                onValueChange = { },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                },
                interactionSource = interactionSource
            )

            if (isPressed) {
                datePickerDialog.show()
            }

            Button(
                onClick = {
                    noticesViewModel.addNotice( noticeName,
                        description,
                        selectedDate,
                        )
                 },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun DisplaySelectedImage(context: Context, uri: Uri) {
    val bitmap = context.contentResolver.openInputStream(uri)?.use {
        android.graphics.BitmapFactory.decodeStream(it)?.asImageBitmap()
    }

    bitmap?.let {
        Image(
            bitmap = it,
            contentDescription = "Selected Image for Notice",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

fun convertUriToByteArray(context: Context, uri: Uri): ByteArray {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val byteArrayOutputStream = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var bytesRead: Int

    if (inputStream != null) {
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }
    }

    return byteArrayOutputStream.toByteArray()
}