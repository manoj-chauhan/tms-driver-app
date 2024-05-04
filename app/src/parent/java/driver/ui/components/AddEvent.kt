package driver.ui.components

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import driver.models.ImagesInfo
import driver.ui.pages.getByteArrayFromUri
import driver.ui.pages.searchPlaces
import driver.ui.viewmodels.EventsViewModel
import driver.ui.viewmodels.PostsViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun addEventPage(profileId: String) {

    val primary = Color(0xFF92A3FD)
    val secondary = Color(0XFF9DCEFF)

    val fontFamily = FontFamily.SansSerif

    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var scope by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var instituteName by remember { mutableStateOf("") }


    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val mTime = remember { mutableStateOf("") }

    val timePickerDialog = android.app.TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            mTime.value = "$hour:$minute"
        }, mHour, mMinute, false
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val interactTime = remember { MutableInteractionSource() }
    val isTimePressed: Boolean by interactTime.collectIsPressedAsState()


    var selectedCoverImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var selectedImageUris by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val isMapToBeShown = remember { mutableStateOf(false); }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }


    var placeName by remember { mutableStateOf("") }
    val postUploadViewModel: PostsViewModel = hiltViewModel()
    val eventsViewModel: EventsViewModel = hiltViewModel()

    var mapView by remember { mutableStateOf<Boolean>(false) }
    var searchText by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(emptyList<Place>()) }
    var selectedPlace by remember { mutableStateOf<String>("") }
    Places.initialize(context, "AIzaSyANMz3n_soyBll2XNWR8inxnDeFb2ipdAc")
    val placesClient: PlacesClient = Places.createClient(context)



    val getCoverImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedCoverImageUri = uri

        uri?.let {
            val mimeType = context.contentResolver.getType(uri)
            val byteArray = getByteArrayFromUri(context, it)
            eventsViewModel.uploadPosts(byteArray, mimeType)
        }

    }

    val getMultipleEventImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris: List<Uri?> ->
        selectedImageUris = uris
        uris.forEach { uri ->
            uri?.let {
                val mimeType = context.contentResolver.getType(uri)
                val byteArray = getByteArrayFromUri(context, it)
                postUploadViewModel.uploadPosts(byteArray, mimeType)
            }
        }
    }

    val mediaId by postUploadViewModel.postDetails.collectAsStateWithLifecycle()
    var mediaPosts = remember { mutableStateListOf<ImagesInfo?>() }

    LaunchedEffect(mediaId) {
        mediaPosts.clear()

        if (mediaId?.isNotEmpty() == true) {
            mediaId?.forEach { media ->
                mediaPosts.add(
                    ImagesInfo(
                        type = when (media.second) {
                            "video/mp4" -> "Video"
                            "image/jpeg", "image/png" -> "Image"
                            else -> "Unknown"
                        },
                        mediaId = media.first,
                        caption = "Command 1"
                    )
                )
            }
        Log.d("mediaPosts", "addEventPage: ${mediaPosts.toList()}")
        }
    }
    val eventViewModel:EventsViewModel= hiltViewModel()
    val coverPhoto by eventViewModel.postDetails.collectAsStateWithLifecycle()
    var coverImage = remember { mutableStateOf<ImagesInfo?>(null) }

    LaunchedEffect(coverPhoto) {
//        coverImage=null

        if (coverPhoto!=null) {
            coverImage.value= ImagesInfo(
                    type = when (coverPhoto!!.second) {
                        "video/mp4" -> "Video"
                        "image/jpeg", "image/png" -> "Image"
                        else -> "Unknown"
                    },
                    mediaId = coverPhoto!!.first,
                    caption = "Command 1"
                )

        }
        Log.d("mediaPosts", "cover photo: ${coverImage.value}")
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth(1f)) {
                Text(
                    text = "NEW EVENT REGISTRATION",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = instituteName,
                onValueChange = { instituteName = it },
                label = { Text("Institute Names") },
                placeholder = { Text("Enter Institute name ") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                placeholder = { Text("Enter Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Enter Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = scope,
                onValueChange = { scope = it },
                label = { Text("Scope ") },
                placeholder = { Text("Enter Scope of Event") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
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
                label = { Text("Date of Event") },
                value = selectedDate,
                onValueChange = {},
                trailingIcon = { Icons.Default.DateRange },
                interactionSource = interactionSource
            )

            if (isPressed) {
                datePickerDialog.show()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Time of Event") },
                value = mTime.value,
                onValueChange = {},
                trailingIcon = { Icons.Default.DateRange },
                interactionSource = interactTime
            )
            if (isTimePressed) {
                timePickerDialog.show()
            }


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select event location",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
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
                Text(
                    text = "Select cover photo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                TextButton(
                    onClick = {

                        getCoverImage.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text(
                        "Select Background", style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))

            if (selectedCoverImageUri != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                ) {
                    AsyncImage(
                        model = selectedCoverImageUri,
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select description photos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                TextButton(
                    onClick = {
                        getMultipleEventImage.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text(
                        "Select Photos", style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            val columns = when (selectedImageUris.size) {
                1 -> 1
                2 -> 2
                3 -> 2
                else -> 2
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(max = 450.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxWidth()

                ) {
                    val imageCount = selectedImageUris.size

                    items(imageCount, key = { it }) { index ->
                        val uri = selectedImageUris[index]
                        if (uri != null) {
                            if (index < 4) {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Selected Image",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .fillMaxHeight()
                                            .align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                        if (index == 3) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .zIndex(1f)
                                    .background(Color.Black.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "+${imageCount - (index + 1)}",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(top = 100.dp, bottom = 104.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier,
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    eventsViewModel.addEvents(title, description, latitude,longitude,selectedPlace, scope, selectedDate, mTime.value,
                       coverImage.value, mediaPosts.toList(), instituteName, profileId)
                 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(primary, secondary)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }



        }

    }


    if (isMapToBeShown.value) {
        markerPosition?.let {
            MapsView(it, selectedPlace = selectedPlace, setShowDialog = {
                isMapToBeShown.value = it
            })
        }
    }
}