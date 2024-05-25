package driver.ui.pages

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
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

    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var isMapToBeShown = remember { mutableStateOf(false); }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    val context = LocalContext.current

    var searchText by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(emptyList<Place>()) }
    var selectedPlace by remember { mutableStateOf<String>("") }
    var mapView by remember { mutableStateOf<Boolean>(false) }
    Places.initialize(context, "AIzaSyANMz3n_soyBll2XNWR8inxnDeFb2ipdAc")
    val placesClient: PlacesClient = Places.createClient(context)





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

                    if(mapView) {
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
                }
                Spacer(modifier = Modifier.height(16.dp))


                Column(
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            if (it.length > 3) {
                                searchPlaces(it, placesClient, context) { places ->
                                    searchResults = places
                                }
                            } else {
                                searchResults = emptyList()
                            }
                        },
                        label = { Text("Enter the place") },
                        placeholder = { Text("Enter the place ") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column {
                        searchResults.forEach { place ->
                            DropdownMenuItem(
                                text = { Text(text = place.name ?: "") },
                                onClick = {
                                    markerPosition = place.latLng
                                    selectedPlace = place.name
                                    mapView = true
                                    searchText = place.name ?: ""
                                    searchResults = emptyList()
                                }
                            )
                        }
                    }
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
                            markerPosition?.latitude.toString(),
                            markerPosition?.longitude.toString()
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
        markerPosition?.let {
            MapsView(
                it,
                selectedPlace,
                setShowDialog = {
                    isMapToBeShown.value = it
            })
        }
    }
}


fun searchPlaces(
    query: String,
    placesClient: PlacesClient,
    context: Context,
    onSearchResult: (List<Place>) -> Unit
) {
    val fields = listOf(Place.Field.NAME, Place.Field.LAT_LNG,  Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS)

    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            val places = mutableListOf<Place>()
            val predictions = response.autocompletePredictions

            val fetchPlaceTasks = predictions.mapNotNull { prediction ->
                prediction.placeId?.let { placeId ->
                    val placeRequest = FetchPlaceRequest.newInstance(placeId, fields)
                    placesClient.fetchPlace(placeRequest)
                        .addOnSuccessListener { fetchPlaceResponse ->
                            places.add(fetchPlaceResponse.place)
                            Log.d("Places", "searchPlaces: $placeRequest")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Autocomplete", "Place not found: ${exception.message}")
                        }
                }
            }

            Tasks.whenAllComplete(fetchPlaceTasks).addOnCompleteListener { _ ->
                onSearchResult(places)
            }
        }
        .addOnFailureListener { exception ->
            Log.e("Autocomplete", "Autocomplete prediction failed: ${exception.message}")
        }
}