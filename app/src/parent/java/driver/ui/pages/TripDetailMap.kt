package driver.ui.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AssignmentDetailScreen(navController: NavHostController, tripId: Int, tripCode: String) {
    Log.d("TAG", "AssignmentDetailScreen: $tripId, $tripCode")
}
