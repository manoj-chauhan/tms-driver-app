package driver.ui.pages

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import driver.models.ContactList
import driver.ui.viewmodels.AddInstitueViewModel
import java.io.IOException


@Composable
fun AddInstitute() {


    var instituteName by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    var facilityFields by remember { mutableStateOf(listOf("")) }
    val contactEntries = remember { mutableStateListOf<ContactList>() }
    val addNewInstitute: AddInstitueViewModel = hiltViewModel()

    val indiaLatLng = LatLng(20.5937, 78.9629)
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    val mapUiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }



    val defaultCameraPositionState = CameraPosition.fromLatLngZoom(indiaLatLng, 4f)
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPositionState
    }

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
                        value = entry.department,
                        onValueChange = { updated ->
                            contactEntries[index] = entry.copy(department = updated)
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
                Text(text = "Mark Institute's Location", fontWeight =FontWeight.Bold , fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(800.dp),
                    onMapLoaded = {  },
                    uiSettings = mapUiSettings,
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,

                    onMapClick = { latLng ->
                        markerPosition = latLng
                    }
                ) {
                    if(markerPosition != null) {
                        Marker(
                            state = MarkerState(
                                position = cameraPositionState.position.target
                            ),
                            draggable = true,
                            title = "Marker Title",
                            onClick = {
                                it.showInfoWindow()
                                true
                            }
                        )
                    }
                }

                if(markerPosition != null) {
                    OutlinedTextField(
                        value = latitude,
                        onValueChange = { null },
                        label = { Text("Latitude") },
                        placeholder = { Text("Latitude") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = longitude,
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

                latitude = cameraPositionState.position.target.latitude.toString()
                longitude = cameraPositionState.position.target.longitude.toString()
                Log.d("Lat Long", "GoogleMapView: ${cameraPositionState.position.target.latitude} ${cameraPositionState.position.target.longitude}")
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
}












