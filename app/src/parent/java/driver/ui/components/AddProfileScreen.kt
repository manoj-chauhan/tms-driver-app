package driver.ui.components

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import driver.Destination
import driver.profileBackGround
import driver.profileLightGray
import driver.ui.pages.searchPlaces
import driver.ui.viewmodels.AccountsProfileViewModel
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfileScreen(navController: NavHostController) {

    val profileViewModel: AccountsProfileViewModel = hiltViewModel()
    val mediaId by profileViewModel.photoDetails.collectAsStateWithLifecycle()
    var name by remember {
        mutableStateOf("")
    }
    var schoolName by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }

    var state by remember {
        mutableStateOf("")
    }

    val typeList = listOf("Student", "Teacher", "Institute")

    var selectedType by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val context = LocalContext.current

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri = result.uriContent
        } else {
            val exception = result.error
        }
    }
    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val cropOptions = CropImageContractOptions(uri, CropImageOptions().apply {
                aspectRatioX = 2
                aspectRatioY = 2
                fixAspectRatio = true
                cropShape = CropImageView.CropShape.RECTANGLE
            })
            imageCropLauncher.launch(cropOptions)
        }

    var imageUploadCompleted by remember { mutableStateOf(false) }

    if (imageUri != null && !imageUploadCompleted) {
        Log.d("Hey", "imageUri: $imageUri")
        val stream = ByteArrayOutputStream()
        bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val byteArray: ByteArray = stream.toByteArray()
        val mimeType = context.contentResolver.getType(imageUri!!)
        profileViewModel.uploadPhoto(byteArray, mimeType)
        imageUploadCompleted = true
    }

    var searchText by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(emptyList<Place>()) }
    var selectedPlace by remember { mutableStateOf<String>("") }
    var mapView by remember { mutableStateOf<Boolean>(false) }
    Places.initialize(context, "AIzaSyANMz3n_soyBll2XNWR8inxnDeFb2ipdAc")
    val placesClient: PlacesClient = Places.createClient(context)

    var markerPosition by remember { mutableStateOf<LatLng?>(null) }


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
                    if (mediaId != null) {
                        val uri = "http://13.201.100.196:8888/test/posts/file/$mediaId"
                        AsyncImage(
                            model = uri,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(150.dp)
                                .height(150.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Edit Icon",
                            tint = profileBackGround,
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        )
                    }
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
                                .clickable { imagePickerLauncher.launch("image/*") }
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                        TextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("City") },
                            placeholder = { Text("Enter City") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                            )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = state,
                            onValueChange = { state = it },
                            label = { Text("State") },
                            placeholder = { Text("Enter State") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                            )
                    }

                    "Teacher" -> {

                        TextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("City") },
                            placeholder = { Text("Enter City") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                            )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = state,
                            onValueChange = { state = it },
                            label = { Text("State") },
                            placeholder = { Text("Enter State") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                            )
                    }

                    "Institute" -> {
                        Column() {
                            TextField(
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
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                                )

                            Column {
                                searchResults.forEach { place ->
                                    DropdownMenuItem(
                                        text = { Text(text = place.address ?: "") },
                                        onClick = {
                                            markerPosition = place.latLng
                                            selectedPlace = place.name
                                            mapView = true
                                            searchText = place.name ?: ""
                                            searchResults = emptyList()

                                            Log.d("place", "AddProfileScreen: $selectedPlace")
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            TextField(
                                value = city,
                                onValueChange = { city = it },
                                label = { Text("City") },
                                placeholder = { Text("Enter City") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                                )

                            Spacer(modifier = Modifier.height(8.dp))

                            TextField(
                                value = state,
                                onValueChange = { state = it },
                                label = { Text("State") },
                                placeholder = { Text("Enter State") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

                                )
                        }
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
                            mediaId?.let {
                                profileViewModel.addProfile(
                                    name,
                                    selectedType,
                                    name,
                                    it,
                                    selectedPlace,
                                    city,
                                    state,
                                    markerPosition
                                )
                            }
                            navController.navigate(Destination.NewHomeScreen)
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