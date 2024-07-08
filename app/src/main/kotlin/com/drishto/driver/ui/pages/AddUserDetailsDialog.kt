package com.drishto.driver.ui.pages

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserDetail(context: Context,setShowDialog: (Boolean) -> Unit) {

    var address by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf<Uri?>(null) }


    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // Handle the selected photo URI
                photoUrl = it
            }
        }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "User Details", style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

//                Button(
//                    onClick = {
//                        // Launch the gallery intent
//                        galleryLauncher.launch("image/*")
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                ) {
//                    Text("Browse Photo")
//                }
//
//                photoUrl?.let { uri ->
//                    Image(
//                        painter = rememberImagePainter(
//                            data = uri,
//                            builder = {
//                            }
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(100.dp)
//                            .padding(8.dp)
//                    )
//                }

                Button(
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    onClick = {
                        setShowDialog(false)
                    }

                ) {
                    Text("Submit")
                }
            }
        }
    }
}
