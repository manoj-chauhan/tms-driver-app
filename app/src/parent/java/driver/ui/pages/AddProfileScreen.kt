package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Approval
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfileScreen() {
    var name by remember {
        mutableStateOf("")
    }

    var type by remember {
        mutableStateOf("")
    }


    var typeExpander by remember {
        mutableStateOf(false)
    }
    val typeList = listOf("Student", "Parent", "Admin")

    var classExpander by remember {
        mutableStateOf(false)
    }
    val classList = listOf("1", "2", "3","4","5","6","7","8","9","10","11","12")
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

            Row (modifier = Modifier.fillMaxWidth()){
                ExposedDropdownMenuBox(
                    expanded = classExpander, modifier = Modifier.fillMaxWidth(0.5f),
                    onExpandedChange = { classExpander = it }
                ) {
                    OutlinedTextField(
                        value = type,
                        label = { Text(text = "Class") },
                        onValueChange = {},
                        readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = classExpander)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Outlined.Class, contentDescription = "Add")
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
                Spacer(modifier = Modifier.width(40.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Section") },

                )
            }
        }
    }

}

@Composable
@Preview
fun ProfilePreview() {
    AddProfileScreen()
}