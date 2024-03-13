package driver.ui.pages

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.drishto.driver.R
import com.drishto.driver.ui.viewmodels.SwipeRefresh
import com.drishto.driver.ui.viewmodels.UserProfileViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import driver.models.ParentPastTrip
import driver.models.ParentTrip
import driver.ui.components.pastTrips
import driver.ui.components.tripList
import driver.ui.viewmodels.parentTripAssigned


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: parentTripAssigned = hiltViewModel(),
    onTripSelected: (assignment: ParentTrip) -> Unit,
    onPastTripSelected: (assignment: ParentPastTrip) -> Unit,
    activity: ComponentActivity
) {
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val context = LocalContext.current

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = runCatching {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }.getOrDefault(false)

    val vw: SwipeRefresh = viewModel()
    val isLoading by vw.isLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val gradient = Brush.linearGradient(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.parseColor("#E8F1F8"))
        ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
    )
    val fontStyle: FontFamily = FontFamily.SansSerif
    val gry = Color(android.graphics.Color.parseColor("#838383"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isConnected) {
                val vm: UserProfileViewModel = hiltViewModel()
                val user by vm.userDetail.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    vm.userDetail(context = context)
                    user?.let { vm.getUploadedImage(it.id) }
                }
                val profile by vm.userImage.collectAsStateWithLifecycle()
                LaunchedEffect(user) {
                    user?.let {
                        vm.getUploadedImage(it.id)
                    }
                }
                Log.d("TAG", "HomeScreen: ${user?.id}")
                Box(
                    modifier = Modifier
                        .height(66.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.White, shape = CircleShape)
                                .width(40.dp)
                                .height(40.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            if (profile != null) {
                                Image(
                                    bitmap = profile!!.asImageBitmap(),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .clip(CircleShape)
                                        .clickable { navController.navigate("user-profile") }
                                        .border(width = 0.dp, Color.White, shape = CircleShape),
                                    contentScale = ContentScale.FillBounds
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Edit Icon",
                                    tint = gry,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clickable { navController.navigate("user-profile") }
                                        .align(Alignment.Center)
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.Feedback,
                            contentDescription = "", modifier = Modifier
                                .height(26.dp)
                                .width(50.dp)
                                .clickable { navController.navigate("notification/${user?.id}") }
                                .zIndex(2f)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(1f)
            ) {
                com.google.accompanist.swiperefresh.SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = vw::loadstuff
                ) {
                    LazyColumn {
                        item {
                            if (isConnected) {
                                val currentAssignmentData by vm.parentTrip.collectAsStateWithLifecycle()
                                vm.fetchParentTrip(context = context)
                                val pastTrip by vm.pastTripList.collectAsStateWithLifecycle()
                                vm.fetchParentPastTrip()

                                if (currentAssignmentData == null) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        LoadingDialog()
                                    }
                                }

                                Column(modifier = Modifier.fillMaxSize()) {
                                    if (currentAssignmentData?.size == 0 && pastTrip?.size == 0) {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            Column(
                                                modifier = Modifier.fillMaxSize(1f),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(200.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "Welcome To Drishto",
                                                        style = TextStyle(
                                                            color = gry,
                                                            fontSize = 20.sp,
                                                            fontWeight = FontWeight.W600,
                                                        )
                                                    )
                                                }
                                                Image(
                                                    painter = painterResource(id = R.drawable.image),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .padding(end = 12.dp)
                                                        .height(250.dp)
                                                        .width(250.dp)
                                                        .clickable { },
                                                    contentScale = ContentScale.FillBounds
                                                )
                                            }
                                        }
                                    }
                                    if (currentAssignmentData?.size != 0) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(13.dp, top = 20.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Text(
                                                text = "Ongoing Trips ",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.W600,
                                                    fontFamily = fontStyle
                                                )
                                            )
                                        }
                                    }
                                    Column {
                                        currentAssignmentData?.let {
                                            Column {
                                                it.take(it.size).forEach { trip ->
                                                    tripList(trip, onTripSelected)
                                                }
                                            }
                                        }
                                        pastTrips(
                                            navHostController = navController,
                                            "home",
                                            onPastTripSelected
                                        )
                                    }
                                }

                            } else {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Toast.makeText(
                                        context,
                                        "Please connect to a network and restart application",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}