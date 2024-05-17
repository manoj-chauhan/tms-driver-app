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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Approval
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import driver.ui.viewmodels.AccountsProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfileScreen(navController: NavHostController) {

    val profileViewModel: AccountsProfileViewModel = hiltViewModel()
    var name by remember {
        mutableStateOf("")
    }
    var type by remember {
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

    var typeExpander by remember {
        mutableStateOf(false)
    }
    val typeList = listOf("Student", "Teacher", "School")

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
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Register Profile",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.W700,
                        fontStyle = FontStyle.Normal
                    )
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(text = "Enter Your Name") },
                leadingIcon = {
                    Icon(Icons.Outlined.Person, contentDescription = "Add")

                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            ExposedDropdownMenuBox(
                expanded = typeExpander, modifier = Modifier.fillMaxWidth(),
                onExpandedChange = { typeExpander = it }
            ) {
                OutlinedTextField(
                    value = type,
                    label = { Text(text = "Select Type ") },
                    onValueChange = {},
                    readOnly = true, trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpander)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Outlined.Approval, contentDescription = "Add")
                    }
                )

                ExposedDropdownMenu(
                    expanded = typeExpander,
                    onDismissRequest = {
                        typeExpander = false
                    },
                    modifier = Modifier.background(Color.White)
                ) {
                    typeList.forEach { profileType ->
                        DropdownMenuItem(
                            text = { Text(text = profileType) },
                            onClick = {
                                type = profileType
                                typeExpander = false
                            })
                    }
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            when (type) {
                "Student" -> {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Student Form",
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W600)
                        )
                    }
                    ExposedDropdownMenuBox(
                        expanded = classExpander, modifier = Modifier.fillMaxWidth(),
                        onExpandedChange = { classExpander = it }
                    ) {
                        OutlinedTextField(
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
                            }
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

                    OutlinedTextField(
                        value = section,
                        onValueChange = {
                            section = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(text = "Section") },
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

                "Teacher" -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Teacher Form",
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W600)
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
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W600)
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
                        profileViewModel.addProfile(name, type, name, childClass, section, session, "", description, childClass, schoolName)
                        navController.navigate("newHomeScreen")
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