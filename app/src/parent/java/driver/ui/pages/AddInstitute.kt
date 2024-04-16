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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp




@Composable
fun AddInstitute(){


        var instituteName by remember { mutableStateOf("") }
        var Contact1 by remember { mutableStateOf("") }
        var Contact2 by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var state by remember { mutableStateOf("") }
        var facility1 by remember { mutableStateOf("") }

    Box(modifier= Modifier
        .fillMaxSize()
        .background(Color.White)) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(10.dp)
        ) {
            Text("Add Your Institute", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(

                value = instituteName,
                onValueChange = { instituteName = it },
                label = { Text("Institute Name") },
                placeholder = { Text("Enter The Institute Name") },
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )  {
                Text("Contact Numbers", fontWeight = FontWeight.Bold)
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Contact")
                }
            }


            OutlinedTextField(
                value = Contact1,
                onValueChange = { Contact1 = it },
                label = { Text("Admission Queries") },
                placeholder = { Text("Enter Admission Contact Number") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = Contact2,
                onValueChange = { Contact2 = it },
                label = { Text("Transport Queries") },
                placeholder = { Text("Enter Transport Contact Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Description", fontWeight = FontWeight.Bold)



            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Enter Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))




            Text("Address", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                placeholder = { Text("Enter Address") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                placeholder = { Text("Enter City") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state,
                onValueChange = { state = it },
                label = { Text("State") },
                placeholder = { Text("Enter State") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )  {
                Text("Facilities", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                IconButton(onClick = { }) {

                    Icon(Icons.Default.Add, contentDescription = "Add Facility")
                }
            }


            OutlinedTextField(
                value = facility1,
                onValueChange = { facility1 = it },
                label = { Text("Facilities") },
                placeholder = { Text("Enter Facilities") },
                modifier = Modifier.fillMaxWidth()
            )



        }
    }
}


