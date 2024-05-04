package driver.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.drishto.driver.R
import driver.ui.viewmodels.NoticesViewModel
import java.io.ByteArrayOutputStream
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import java.util.Calendar
import java.util.Date
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Composable
fun AddNoticeEvent() {

    val fontFamily = FontFamily.SansSerif

    var noticeName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val addNewNotice: NoticesViewModel = hiltViewModel()

    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedFileUri = uri
        }
    )

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, dayOfMonth: Int ->
            selectedDate = "$dayOfMonth-${selectedMonth + 1}-$selectedYear"
        },
        year,
        month,
        day
    )

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
                placeholder = { Text("Enter Notice Heading") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Description", fontWeight = FontWeight.Light)

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Enter Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { pickFileLauncher.launch("*/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select File")
            }

            Spacer(modifier = Modifier.height(8.dp))

            selectedFileUri?.let { uri ->
                DisplaySelectedFile(context, uri)
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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val fileByteArray = selectedFileUri?.let { convertUriToByteArray(context, it) }
                    if (fileByteArray == null) {
                        Toast.makeText(context, "Please select a file to submit.", Toast.LENGTH_SHORT).show()
                    } else {
                        addNewNotice.addNotice(
                            noticeName,
                            selectedDate,
                            description,
                            fileByteArray
                        )
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}


@Composable
fun DisplaySelectedFile(context: Context, uri: Uri) {
    val fileType = remember { getFileType(context, uri) }

    if (fileType == "image") {
        val bitmap = context.contentResolver.openInputStream(uri)?.use {
            android.graphics.drawable.Drawable.createFromStream(it, uri.toString())
                ?.let { it1 -> androidx.core.graphics.drawable.DrawableCompat.wrap(it1).toBitmap().asImageBitmap() }
        }

        bitmap?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    bitmap = it,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    } else {
        Row(modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
            Image(
                painter = painterResource(id = R.drawable.doc),
                contentDescription = "Selected File",

                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(4.dp)
            )

        }

    }
}

fun getFileType(context: Context, uri: Uri): String {
    val mimeType = context.contentResolver.getType(uri)
    return if (mimeType != null && mimeType.startsWith("image/")) {
        "image"
    } else {
        "other"
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
        inputStream.close()
    }

    return byteArrayOutputStream.toByteArray()
}
