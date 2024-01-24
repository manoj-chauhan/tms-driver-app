package driver.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DrishtoParentApp() {
    val navController = rememberNavController()
    AppNavigationHost(
        navController = navController
    )
}