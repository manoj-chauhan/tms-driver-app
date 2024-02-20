package com.drishto.driver.ui.pages

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.drishto.driver.R
import com.drishto.driver.ui.viewmodels.ChildrenListViewModel
import com.drishto.driver.ui.viewmodels.CompanyPositions
import com.drishto.driver.ui.viewmodels.UserProfileViewModel
import driver.SetPasswordActivity
import java.io.ByteArrayOutputStream
import java.lang.reflect.Field
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun UserProfile() {
    val context = LocalContext.current
    val vm: UserProfileViewModel = hiltViewModel()


    val isDetailsSelected = remember { mutableStateOf(false); }

    val isEditNameSelected = remember { mutableStateOf(false); }

    val isDialogVisible = remember { mutableStateOf(false); }


    val userDetail by vm.userDetail.collectAsStateWithLifecycle()
    vm.userDetail(context = context)

    val filteredCompanies = userDetail?.companiesList?.filter { company ->
        "DRIVER" in company.roles
    }

    val buildConfigClass: Class<*> = Class.forName("com.drishto.driver.BuildConfig")
    val buildVariantField: Field = buildConfigClass.getDeclaredField("BUILD_VARIANT")
    val buildVariantValue: String = buildVariantField.get(null) as String


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = 20.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .zIndex(2f)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.signal),

                    contentDescription = null, // Provide a proper content description
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(2f)
                )


            }
            Card(
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxWidth()
                    .fillMaxSize(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(35.dp, 35.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 36.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End
                    ) {

                        Button(onClick = { isDetailsSelected.value = true }) {
                            Text(text = "Add Details")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${userDetail?.name}", style = TextStyle(

                                color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Icon",

                            modifier = Modifier
                                .size(25.dp)
                                .clickable { isEditNameSelected.value = true }
                        )
                    }
                    Row {
                        Text(
                            text = "(${userDetail?.userName})", style = TextStyle(
                                color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                    if (buildVariantValue == "driver") {
                        if (filteredCompanies != null) {
                            companyList(filteredCompanies)
                            Log.d("Variant", "UserProfile: $buildVariantValue")
                        }
                    } else {
                        childList()

                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Age", style = TextStyle(
                                color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(20.dp) // Adjust the size as needed
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier.width(300.dp)) {
                            Text(
                                text = "Address ",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                            contentDescription = "Edit Icon",
                            modifier = Modifier.size(20.dp) // Adjust the size as needed
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Log.d("TAG", "UserProfile: ${userDetail?.authProvider}")

                    if (userDetail?.authProvider == "google.com") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { isDialogVisible.value = true }
                            ) {
                                Text("Set Password")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Driving License", style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.Edit, // Use the Edit icon from Icons.Default
                                contentDescription = "Edit Icon",
                                modifier = Modifier.size(25.dp) // Adjust the size as needed
                            )

                        }

                    }

                }
            }
        }
    }

    if (isDetailsSelected.value) {

        AddUserDetail(context,
            setShowDialog = {
                isDetailsSelected.value = it
            })

    }

    if (isEditNameSelected.value) {
        userDetail?.name?.let {
            EditUserName(context, it,
                setShowDialog = {
                    isEditNameSelected.value = it
                }
            )
        }
    }


    if (isDialogVisible.value) {
        val myIntent = Intent(context, SetPasswordActivity::class.java)
        context.startActivity(myIntent)
    }

}
fun calculateAge(dateOfBirth: String): Number {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val birthDate = LocalDate.parse(dateOfBirth, dateFormatter)

    val currentDate = LocalDate.now()

    val age = ChronoUnit.YEARS.between(birthDate, currentDate).toInt()

    return age
}
@Composable
fun AgeDisplay(dateOfBirth: String) :Number {
    val age = calculateAge(dateOfBirth)
    return age
}

@Composable
fun childList() {
    val vm: ChildrenListViewModel = hiltViewModel()

    val childrensList by vm.childrensList.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        vm.getChildrenList()
    }

    val gry = Color(android.graphics.Color.parseColor("#838383"))
    val fontStyle: FontFamily = FontFamily.SansSerif
    var age:Number= 0
    if(childrensList?.size  != 0 ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Children",
                style = TextStyle(
                    color = gry,
                    fontSize = 14.sp,
                    fontFamily = fontStyle,
                    fontWeight = FontWeight.W400
                )
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

    childrensList?.forEach { children ->

        children.dateOfBirth.let {
            age =AgeDisplay(dateOfBirth = it)
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${children.name} s/o hard COded ",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W600
                    )
                )

                Text(
                    text = "$age yrs",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W400
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = children.schoolName,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W400
                    )
                )

                Text(
                    text = "${children.standard} Std",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W400
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun companyList(filteredCompanies: List<CompanyPositions>) {
    Row {
        Text(
            text = "Companies List", style = TextStyle(
                color = Color.Black, fontSize = 19.sp, fontWeight = FontWeight.Bold
            )
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    filteredCompanies.forEach { company ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = company.companyCode, style = TextStyle(
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = company.companyName, style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun userProfileView(navController: NavHostController) {
    val gradient = Brush.linearGradient(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.parseColor("#E8F1F8"))
        ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
    )


    val context = LocalContext.current
    val vm: UserProfileViewModel = hiltViewModel()
    val userDetail by vm.userDetail.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.userDetail(context = context)
        userDetail?.let { vm.getUploadedImage(it.id) }
    }
    val userProfile by vm.userImage.collectAsStateWithLifecycle()
    LaunchedEffect(userDetail) {
        userDetail?.let {
            vm.getUploadedImage(it.id)
        }
    }
    val gry = Color(android.graphics.Color.parseColor("#838383"))
    val fontStyle: FontFamily = FontFamily.SansSerif

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

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
                aspectRatioX = 4
                aspectRatioY = 4
                fixAspectRatio = false
                maxCropResultWidth = 550
                maxCropResultHeight = 550
                minCropWindowWidth = 550
                minCropWindowHeight = 550
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
        userDetail?.id?.let { sendToServer(file = byteArray, it) }
        imageUploadCompleted = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .align(Alignment.Start)
                    .padding(top = 20.dp, end = 20.dp)
            ) {
                Box(modifier = Modifier
                    .width(50.dp)
                    .padding(end = 20.dp)
                    .fillMaxHeight()
                    .clickable { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Edit Icon",
                        modifier = Modifier
                            .fillMaxSize()
                            .size(15.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                    )
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.23f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = CircleShape)
                            .width(150.dp)
                            .height(150.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        if (userProfile != null) {
                            userProfile?.let { loadedBitmap ->
                                Image(
                                    bitmap = loadedBitmap.asImageBitmap(),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(150.dp)
                                        .clip(CircleShape)
                                        .border(width = 0.dp, Color.White, shape = CircleShape),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, shape = CircleShape)
                                    .width(150.dp)
                                    .height(150.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = "Edit Icon",
                                    tint = gry,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .align(Alignment.Center)
                                )
                            }

                        }
                        Box(
                            modifier = Modifier
                                .background(Color.White, shape = CircleShape)
                                .width(50.dp)
                                .height(50.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Edit Icon",
                                tint = gry,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable { imagePickerLauncher.launch("image/*") }
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize(1f)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(66.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            userDetail?.name?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 24.sp,
                                        fontFamily = fontStyle,
                                        fontWeight = FontWeight.W700
                                    )
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Icon",
                                tint = gry,
                                modifier = Modifier
                                    .size(28.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Username",
                                style = TextStyle(
                                    color = gry,
                                    fontSize = 14.sp,
                                    fontFamily = fontStyle,
                                    fontWeight = FontWeight.W400
                                )
                            )

                            userDetail?.userName?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = fontStyle,
                                        fontWeight = FontWeight.W400
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(45.dp))
                        childList()
                    }
                }
            }
        }
    }
}


@Composable
fun sendToServer(file: ByteArray?, userId:Int) {
    val ui: UserProfileViewModel = hiltViewModel()
    ui.uploadImage(file, userId = userId)
}