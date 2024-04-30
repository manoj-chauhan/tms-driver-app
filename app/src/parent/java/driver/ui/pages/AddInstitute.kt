package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import driver.models.ContactList
import driver.ui.components.MapsView
import driver.ui.viewmodels.AddInstitueViewModel


@Composable
fun AddInstitute(navController: NavHostController) {


    var instituteName by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    val contactEntries = remember { mutableStateListOf<ContactList>() }

    var facilityFields by remember { mutableStateOf(listOf("")) }
    val addNewInstitute: AddInstitueViewModel = hiltViewModel()

    val indiaLatLng = LatLng(20.5937, 78.9629)
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var isMapToBeShown = remember { mutableStateOf(false); }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            item {
                Text("Register Your Institute", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = instituteName,
                    onValueChange = { instituteName = it },
                    label = { Text("Institute Name") },
                    placeholder = { Text("Enter Institute Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Contact Numbers",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    IconButton(onClick = {

                        contactEntries.add(ContactList("", ""))
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Contact")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                contactEntries.forEachIndexed { index, entry ->
                    OutlinedTextField(
                        value = entry.description,
                        onValueChange = { updated ->
                            contactEntries[index] = entry.copy(description = updated)
                        },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = entry.number,
                        onValueChange = { updated ->
                            contactEntries[index] = entry.copy(number = updated)
                        },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

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
                Row(modifier =  Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Mark Institute's Location", fontWeight =FontWeight.Bold , fontSize = 18.sp)
                    TextButton(
                        onClick = {
                            isMapToBeShown.value = true
                        }
                    ) {
                        Text(
                            "View Map", style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
//                GoogleMap(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(800.dp),
//                    onMapLoaded = {  },
//                    uiSettings = mapUiSettings,
//                    cameraPositionState = cameraPositionState,
//                    properties = mapProperties,
//
//                    onMapClick = { latLng ->
//                        markerPosition = latLng
//                    }
//                ) {
//                    if(markerPosition != null) {
//                        Marker(
//                            state = MarkerState(
//                                position = cameraPositionState.position.target
//                            ),
//                            draggable = true,
//                            title = "Marker Title",
//                            onClick = {
//                                it.showInfoWindow()
//                                true
//                            }
//                        )
//                    }
//                }

                if(markerPosition != null) {
                    latitude = markerPosition!!.latitude.toString()
                    longitude = markerPosition!!.longitude.toString()
                    OutlinedTextField(
                        value = latitude,
                        enabled = false,
                        onValueChange = { null },
                        label = { Text("Latitude") },
                        placeholder = { Text("Latitude") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = longitude,
                        enabled = false,
                        onValueChange = { null },
                        label = { Text("Longitude") },
                        placeholder = { Text("Longitude") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Facilities", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                    IconButton(onClick = {
                        // Add a new facility field when the button is clicked
                        facilityFields = facilityFields.toMutableList().also { it.add("") }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Facility")
                    }

                }
                facilityFields.forEachIndexed { index, facility ->
                    OutlinedTextField(
                        value = facility,
                        onValueChange = { newValue ->
                            facilityFields =
                                facilityFields.toMutableList().also { it[index] = newValue }
                        },
                        label = { Text("Facility $index") },
                        placeholder = { Text("Enter Facility") },
                        modifier = Modifier.fillMaxWidth()
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        addNewInstitute.addInstitute(
                            instituteName,
                            contactEntries.toList(),
                            description,
                            facilityFields,
                            address,
                            state,
                            city,
                            latitude,
                            longitude
                        );
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

    if(isMapToBeShown.value){
        MapsView(onMarkerSelected ={
                markerPosition = it
            }, setShowDialog = {
                isMapToBeShown.value = it
        })
    }
}












