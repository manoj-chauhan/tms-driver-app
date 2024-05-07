package driver.ui.pages

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import driver.models.Event

@Composable
fun EventsListPage(navController: NavHostController, onRegisterClick: (event: Event) -> Unit) {
    Eventpage(onRegisterClick = onRegisterClick)
}
