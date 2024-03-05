package driver.ui.pages

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addStudentInPlan() {

    var name by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var gender by remember { mutableStateOf("") }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        20.dp
                    ),
                contentAlignment = Alignment.Center
            )
            {


                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(modifier = Modifier.fillMaxWidth(1f)) {
                        Text(
                            text = "NEW STUDENT",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
                        )
                    }
                    Column {

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(modifier = Modifier.fillMaxWidth(1f)) {
                            Text(
                                text = "Personal Details",
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500)
                            )
                        }
                        Column(modifier = Modifier) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Student Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Guardian Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )


                            val datePickerDialog =
                                DatePickerDialog(
                                    context,
                                    { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                        val newDate = Calendar.getInstance()
                                        newDate.set(year, month, dayOfMonth)
                                        selectedDate = " ${month} / $dayOfMonth / $year"
                                    },
                                    year,
                                    month,
                                    day
                                )

                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                readOnly = true,
                                label = { Text("Date of Birth") },
                                value = selectedDate,
                                onValueChange = {},
                                trailingIcon = { Icons.Default.DateRange },
                                interactionSource = interactionSource
                            )

                            if (isPressed) {
                                datePickerDialog.show()
                            }
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = it }) {
                                OutlinedTextField(
                                    value = gender,
                                    label = { Text(text = "Gender") },
                                    onValueChange = {},
                                    readOnly = true, trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )

                                ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                                    expanded = false
                                }, modifier = Modifier.background(Color.White)) {

                                    DropdownMenuItem(text = { Text(text = "Male") }, onClick = {
                                        gender = "Male"
                                        selectedText = "M"
                                        expanded = false
                                    })

                                    DropdownMenuItem(text = { Text(text = "Female") }, onClick = {
                                        gender = "Female"
                                        selectedText = "F"
                                        expanded = false
                                    })
                                }
                            }


                        }

                        Spacer(modifier = Modifier.height(26.dp))

                        Row(modifier = Modifier.fillMaxWidth(1f)) {
                            Text(
                                text = "School Detail",
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500)
                            )
                        }
                        Column(modifier = Modifier) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("UerName") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("UerName") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("UerName") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )

                        }


                        Spacer(modifier = Modifier.height(26.dp))

                        Row(modifier = Modifier.fillMaxWidth(1f)) {
                            Text(
                                text = "Phone Number",
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500)
                            )
                        }
                        Column(modifier = Modifier) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("UerName") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("UerName") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )


                        }

                        Spacer(modifier = Modifier.height(26.dp))

//                        Row(modifier = Modifier.fillMaxWidth(1f)) {
//                            Text(
//                                text = "Locations",
//                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500)
//                            )
//                        }
//                        Column(modifier = Modifier) {
//                            OutlinedTextField(
//                                value = name,
//                                onValueChange = { name = it },
//                                label = { Text("UerName") },
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(bottom = 1.dp)
//                            )
//                            OutlinedTextField(
//                                value = name,
//                                onValueChange = { name = it },
//                                label = { Text("UerName") },
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(bottom = 1.dp)
//                            )
//
//
//                        }
                    }
                }
            }

        }
    }
}

@Composable
@Preview
fun addStudentInPlanPreview() {
    addStudentInPlan()
}