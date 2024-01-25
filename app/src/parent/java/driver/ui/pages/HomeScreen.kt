package driver.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.ui.viewmodels.parentTripAssigned


@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: parentTripAssigned = hiltViewModel(),
) {
    val context = LocalContext.current

    val currentAssignmentData by vm.parentTrip.collectAsStateWithLifecycle()
    vm.fetchParentTrip(context = context)

    Log.d("ParentTrip", "HomeScreen: $currentAssignmentData")

    Box(modifier = Modifier.fillMaxSize()) {

    }
}
