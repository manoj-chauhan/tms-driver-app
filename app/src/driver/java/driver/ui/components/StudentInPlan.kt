package driver.ui.components

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.ui.viewmodels.DriverPlanDetailsViewModel
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentInPlan(operatorId: Int, planId: Int,  navController: NavHostController) {
    Log.d("Dialog", "addStudentInPlan: $operatorId, $planId ")
    val context = LocalContext.current

    val ch: DriverPlanDetailsViewModel = hiltViewModel()
    val schedules by ch.planList.collectAsStateWithLifecycle()
    ch.fetchSchedule(context = context, operatorId, planId)


    Log.d("Dialog", "addStudentInPlan: $operatorId, $planId ")
    var name by remember { mutableStateOf("") }
    var guardianName by remember { mutableStateOf("") }
    var schoolName by remember { mutableStateOf("") }
    var schoolAddress by remember { mutableStateOf("") }
    var primaryPhone by remember { mutableStateOf("") }
    var secondaryPhone by remember { mutableStateOf("") }
    var primarynumber: String = ""
    var secondarynumber: String = ""

    var isPrimaryNumberNotValid by remember {
        mutableStateOf(false)
    }
    var primaryPhoneError by remember {
        mutableStateOf("")
    }

    var isNameValid by remember {
        mutableStateOf(false)
    }
    var nameError by remember {
        mutableStateOf("")
    }


    var guardianValid by remember {
        mutableStateOf(false)
    }
    var guardianError by remember {
        mutableStateOf("")
    }


    var schoolNameValid by remember {
        mutableStateOf(false)
    }
    var schoolNameError by remember {
        mutableStateOf("")
    }


    var schoolAddressValid by remember {
        mutableStateOf(false)
    }
    var schoolAddressError by remember {
        mutableStateOf("")
    }
    var standardSelected by remember {
        mutableStateOf(false)
    }
    var standardSelectedError by remember {
        mutableStateOf("")
    }

    var boardingSelected by remember {
        mutableStateOf(false)
    }
    var boardingSelectedError by remember {
        mutableStateOf("")
    }

    var deboardingSelected by remember {
        mutableStateOf(false)
    }
    var deboardingSelectedError by remember {
        mutableStateOf("")
    }

    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    var expanded by remember { mutableStateOf(false) }
    var standardexpander by remember { mutableStateOf(false) }
    var boardinglocationexpander by remember { mutableStateOf(false) }
    var deboardinglocationexpander by remember { mutableStateOf(false) }


    var selectedText by remember { mutableStateOf("") }

    var gender by remember { mutableStateOf("") }
    var standard by remember { mutableStateOf("") }
    var boardingPlaceName by remember { mutableStateOf("") }
    var boardingPlaceId by remember { mutableIntStateOf(0) }

    var deboardingPlaceName by remember { mutableStateOf("") }
    var deboardingPlaceId by remember { mutableIntStateOf(0) }


    val schoolStandard =
        arrayOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII")


    fun validatePrimaryPhone(): Boolean {
        if (primaryPhone.length < 10) {
            isPrimaryNumberNotValid = true
            primaryPhoneError = "Phone Number is not valid"
            return true
        } else {
            isPrimaryNumberNotValid = false
            primaryPhoneError = ""
            return false
        }
    }
    fun validateName(): Boolean {
        if (name.isNotEmpty()) {
            isNameValid = true
            return true
        } else {
            isNameValid = false
            nameError = "Enter the student name"
            return false
        }
    }
    fun standardSelected(): Boolean {
        if (standard.isBlank()) {
            standardSelected = false
            standardSelectedError = "Select the standard"
            return false
        } else {
            standardSelected = true
            return true
        }
    }
    fun boardingSelected(): Boolean {
        if (boardingPlaceName.isEmpty()) {
            boardingSelected = false
            boardingSelectedError = "Select the boarding place"
            return false
        } else {
            boardingSelected = true
            return true
        }
    }
    fun deboardingSelected(): Boolean {
        if (deboardingPlaceName.isEmpty()) {
            deboardingSelected = false
            deboardingSelectedError = "Select the deboarding place"
            return false
        } else {
            deboardingSelected = true
            return true
        }
    }
    fun validateGuardianName(): Boolean {
        if (guardianName.isNotEmpty()) {
            guardianValid = true
            return true
        } else {
            guardianValid = false
            guardianError = "Enter the Guardian name"
            return false
        }
    }
    fun validateSchoolName(): Boolean {
        if (schoolName.isNotEmpty()) {
            schoolNameValid = true
            return true
        } else {
            schoolNameValid = false
            schoolNameError = "Enter the school name"
            return false
        }
    }
    fun validateSchoolAddress(): Boolean {
        if (schoolAddress.isNotEmpty()) {
            schoolAddressValid = true
            return true
        } else {
            schoolAddressValid = false
            schoolAddressError = "Enter the School address"
            return false
        }
    }


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
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

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
                                label = { Text("Student Name *") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            if (name.isEmpty()) {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = nameError,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                            }
                            OutlinedTextField(
                                value = guardianName,
                                onValueChange = { guardianName = it },
                                label = { Text("Guardian Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            if (guardianName.isEmpty()) {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = guardianError,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                            }
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
                            ExposedDropdownMenuBox(
                                expanded = standardexpander, modifier = Modifier.fillMaxWidth(),
                                onExpandedChange = { standardexpander = it }
                            ) {
                                OutlinedTextField(
                                    value = standard,
                                    label = { Text(text = "Standard") },
                                    onValueChange = {},
                                    readOnly = true, trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = standardexpander)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )

                                ExposedDropdownMenu(
                                    expanded = standardexpander,
                                    onDismissRequest = {
                                        standardexpander = false
                                    },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    schoolStandard.forEach { standardClass ->
                                        DropdownMenuItem(
                                            text = { Text(text = standardClass) },
                                            onClick = {
                                                standard = standardClass
                                                standardexpander = false
                                            })
                                    }
                                }
                            }
                            if (standard.isBlank()) {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = standardSelectedError,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                            }
                            OutlinedTextField(
                                value = schoolName,
                                onValueChange = { schoolName = it },
                                label = { Text("School Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            if (schoolName.isEmpty()) {

                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = schoolNameError,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                            }
                            OutlinedTextField(
                                value = schoolAddress,
                                onValueChange = { schoolAddress = it },
                                label = { Text("School Address") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                            )
                            if(schoolAddress.isEmpty()) {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = schoolAddressError,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                            }
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
                                value = primaryPhone,
                                onValueChange = { newValue ->
                                    if (newValue.length <= 10) {
                                        primaryPhone = newValue
                                    }
                                },
                                label = { Text("Primary Phone Number") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                leadingIcon = {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = "+91",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                        )
                                    }
                                }
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = primaryPhoneError,
                                fontSize = 14.sp,
                                color = Color.Red
                            )
                            OutlinedTextField(
                                value = secondaryPhone,
                                onValueChange = { secondaryPhone = it },
                                label = { Text("Secondary Phone Number") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp),
                                leadingIcon = {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = "+91",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                        )
                                    }
                                }
                            )


                        }

                        Spacer(modifier = Modifier.height(26.dp))

                        Row(modifier = Modifier.fillMaxWidth(1f)) {
                            Text(
                                text = "Locations",
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500)
                            )
                        }
                        Column(modifier = Modifier) {
                            ExposedDropdownMenuBox(
                                expanded = boardinglocationexpander,
                                modifier = Modifier.fillMaxWidth(),
                                onExpandedChange = { boardinglocationexpander = it }
                            ) {
                                OutlinedTextField(
                                    value = boardingPlaceName,
                                    label = { Text(text = "Boarding Location") },
                                    onValueChange = {},
                                    readOnly = true, trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = boardinglocationexpander)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )

                                ExposedDropdownMenu(
                                    expanded = boardinglocationexpander,
                                    onDismissRequest = {
                                        boardinglocationexpander = false
                                    },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    schedules?.tripPlanScheduleList?.forEach { place ->
                                        DropdownMenuItem(
                                            text = { Text(text = place.placeName) },
                                            onClick = {
                                                boardingPlaceName = place.placeName
                                                boardingPlaceId = place.placeId
                                                Log.d(
                                                    "Dialog",
                                                    "addStudentInPlan: $boardingPlaceId "
                                                )

                                                boardinglocationexpander = false
                                            })
                                    }
                                }
                            }
                            if (boardingPlaceName.isEmpty()) {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = boardingSelectedError,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                            }
                            ExposedDropdownMenuBox(
                                expanded = deboardinglocationexpander,
                                modifier = Modifier.fillMaxWidth(),
                                onExpandedChange = { deboardinglocationexpander = it }
                            ){
                                OutlinedTextField(
                                    value = deboardingPlaceName,
                                    label = { Text(text = "DeBoarding Location") },
                                    onValueChange = {},
                                    readOnly = true, trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = deboardinglocationexpander)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )

                                ExposedDropdownMenu(
                                    expanded = deboardinglocationexpander,
                                    onDismissRequest = {
                                        deboardinglocationexpander = false
                                    },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    schedules?.tripPlanScheduleList?.forEach { place ->
                                        DropdownMenuItem(
                                            text = { Text(text = place.placeName) },
                                            onClick = {
                                                deboardingPlaceName = place.placeName
                                                deboardingPlaceId = place.placeId
                                                Log.d(
                                                    "Dialog",
                                                    "addStudentInPlan: $deboardingPlaceId "
                                                )
                                                deboardinglocationexpander = false
                                            })
                                    }
                                }
                            }
                            if (deboardingPlaceName.isEmpty()) {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = deboardingSelectedError,
                                    fontSize = 14.sp,
                                    color = Color.Red
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(26.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(), verticalAlignment = Alignment.Bottom
                        ) {
                            primarynumber = "+91$primaryPhone"
                            if (secondaryPhone.isNotEmpty()) {
                                secondarynumber = "+91$secondaryPhone"
                            } else {
                                secondarynumber = ""
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .align(Alignment.Bottom),
                                onClick = {
                                    if (!(validatePrimaryPhone()) && validateName() && validateGuardianName()  && validateSchoolName() && validateSchoolAddress() && standardSelected() && boardingSelected() && deboardingSelected()) {
                                        Log.d("true", "addStudentInPlan: ")
                                        ch.addStudentInPlan(
                                            name,
                                            guardianName,
                                            selectedDate,
                                            selectedText,
                                            standard,
                                            schoolName,
                                            schoolAddress,
                                            primarynumber,
                                            secondarynumber,
                                            boardingPlaceId,
                                            deboardingPlaceId,
                                            planId,
                                            operatorId
                                        )
                                        navController.popBackStack()
                                    }

                                },
                                contentPadding = PaddingValues(),
                                colors = ButtonDefaults.buttonColors(
                                    Color.Transparent
                                ),
                            ) {
                                val primary = Color(0xFF92A3FD)
                                val secondary = Color(0XFF9DCEFF)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(38.dp)
                                        .align(Alignment.Bottom)
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                listOf(
                                                    primary,
                                                    secondary
                                                )
                                            ),
                                            shape = RoundedCornerShape(40.dp)
                                        ), contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Add Student",
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
        }
    }
}