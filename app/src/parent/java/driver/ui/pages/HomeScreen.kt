package driver.ui.pages

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.drishto.driver.PhoneNumberActivity
import com.drishto.driver.network.clearSession
import com.drishto.driver.ui.viewmodels.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import driver.models.ParentTrip
import driver.ui.components.pastTrips
import driver.ui.components.tripList
import driver.ui.viewmodels.parentTripAssigned


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: parentTripAssigned = hiltViewModel(),
    onTripSelected: (assignment: ParentTrip) -> Unit
) {
    val context = LocalContext.current

    var expander by remember { mutableStateOf(false) }
    var userProfile by remember { mutableStateOf(false) }

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = runCatching {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }.getOrDefault(false)

    val vw: SwipeRefresh = viewModel()
    val isLoading by vw.isLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(android.graphics.Color.parseColor("#EDF4FA")),
            Color(android.graphics.Color.parseColor("#E8F1F8")),
        ),
        startY = 0.0f,
        endY = 900.0f,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {

        Box(modifier = Modifier.height(68.dp)) {
            Scaffold(
                containerColor = Color(android.graphics.Color.parseColor("#F1F6FB")),
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .padding(end = 13.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(
                                android.graphics.Color.parseColor(
                                    "#F1F6FB"
                                )
                            )
                        ),
                        title = { Text(text = "") },
                        actions = {
                            IconButton(onClick = { expander = true }) {
                                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                            }

                            DropdownMenu(
                                expanded = expander,
                                modifier = Modifier.align(Alignment.CenterVertically),
                                onDismissRequest = { expander = false }) {
                                DropdownMenuItem(text = { Text(text = "User Profile") },
                                    onClick = { userProfile = true; expander = false },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Person,
                                            contentDescription = null
                                        )
                                    })

                                DropdownMenuItem(text = { Text(text = "Log out") },
                                    onClick = {
                                        val myIntent =
                                            Intent(context, PhoneNumberActivity::class.java)
                                        clearSession(context)
                                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        context.startActivity(myIntent)
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Logout,
                                            contentDescription = null
                                        )
                                    })
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    LazyColumn(
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .graphicsLayer(
                    alpha = 1f,
                    translationY = (20).dp.value
                )
        ) {
            if (isConnected) {
                val currentAssignmentData by vm.parentTrip.collectAsStateWithLifecycle()
                vm.fetchParentTrip(context = context)

                Column(modifier = Modifier.fillMaxSize()) {
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
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    if (currentAssignmentData?.size == 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(13.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "No active trips!!",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }

                    com.google.accompanist.swiperefresh.SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = vw::loadstuff
                    ) {
                        currentAssignmentData?.let {
                            LazyColumn {
                                items(it) { trip ->
                                    tripList(trip, onClick = onTripSelected)
//                                    AssignedTrip(trip, onClick = onTripSelected)
                                }
                            }
                        }
                    }

                    pastTrips(navHostController = navController, "home")
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
        if (userProfile) {
            navController.navigate("user-profile")
            userProfile = false
        }
    }
}