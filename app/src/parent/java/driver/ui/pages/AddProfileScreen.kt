package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import driver.profileBackGround
import driver.profileLightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfileScreen() {

//    val profileViewModel: AccountsProfileViewModel = hiltViewModel()
    var name by remember {
        mutableStateOf("")
    }
    var section by remember {
        mutableStateOf("")
    }
    var childClass by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var schoolName by remember {
        mutableStateOf("")
    }
    var session by remember {
        mutableStateOf("")
    }

    val typeList = listOf("Student", "Teacher", "School")

    var selectedType by remember {
        mutableStateOf("")
    }
    var classExpander by remember {
        mutableStateOf(false)
    }
    val classList = listOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(profileBackGround)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Register Profile",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W600,
                                    fontStyle = FontStyle.Normal,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = 25.dp)
                        .background(profileLightGray, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Edit Icon",
                        tint = profileBackGround,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = CircleShape)
                            .size(50.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Edit Icon",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {

                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = "Enter Your Name") },
                    leadingIcon = {
                        Icon(Icons.Outlined.Person, contentDescription = "Add")

                    },
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Select Type",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600
                        )
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                        typeList.forEach { type ->
                            RadioButton(
                                selected = selectedType == type,
                                onClick = { selectedType = type })
                            Text(text = type)
                        }
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))

                when (selectedType) {
                    "Student" -> {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Student Form",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W600
                                )
                            )
                        }
                        ExposedDropdownMenuBox(
                            expanded = classExpander, modifier = Modifier.fillMaxWidth(),
                            onExpandedChange = { classExpander = it }
                        ) {
                            TextField(
                                value = childClass,
                                label = { Text(text = "Select Class ") },
                                onValueChange = {},
                                readOnly = true, trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = classExpander)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Outlined.School, contentDescription = "Add")
                                },
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                            )

                            ExposedDropdownMenu(
                                expanded = classExpander,
                                onDismissRequest = {
                                    classExpander = false
                                },
                                modifier = Modifier.background(Color.White)
                            ) {
                                classList.forEach { classType ->
                                    DropdownMenuItem(
                                        text = { Text(text = classType) },
                                        onClick = {
                                            childClass = classType
                                            classExpander = false
                                        })
                                }
                            }
                        }

                        TextField(
                            value = section,
                            onValueChange = {
                                section = it
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "Section") },
                            leadingIcon = {
                                Icon(Icons.Outlined.Person, contentDescription = "Add")

                            },
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                        )

                        TextField(
                            value = session,
                            onValueChange = {
                                session = it
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "Session") },
                            leadingIcon = {
                                Icon(Icons.Outlined.Class, contentDescription = "Add")

                            },
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                        )
                    }

                    "Teacher" -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Teacher Form",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W600
                                )
                            )
                        }
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "Enter Description") },
                            leadingIcon = {
                                Icon(Icons.Outlined.Person, contentDescription = "Add")
                            }
                        )


                        OutlinedTextField(
                            value = session,
                            onValueChange = {
                                session = it
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "Session") },
                            leadingIcon = {
                                Icon(Icons.Outlined.Class, contentDescription = "Add")

                            }
                        )
                    }

                    "School" -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "School Form",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W600
                                )
                            )
                        }
                        OutlinedTextField(
                            value = schoolName,
                            onValueChange = { schoolName = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "Enter School Name") },
                            leadingIcon = {
                                Icon(Icons.Outlined.Person, contentDescription = "Add")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(), verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .align(Alignment.Bottom),
                        enabled = true,
                        onClick = {
//                        profileViewModel.addProfile(name, type, name, childClass, section, session, "", description, childClass, schoolName)
//                        navController.navigate("newHomeScreen")
                        },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        shape = RoundedCornerShape(40.dp)
                    ) {
                        val primary = Color(0xFF92A3FD)
                        val secondary = Color(0XFF9DCEFF)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(40.dp)
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
                                    text = "Register",
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

@Preview
@Composable
fun ProfileAdd() {
    AddProfileScreen()
}