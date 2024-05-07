package driver.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import driver.models.Event

@Composable
fun EventsListPage(navController: NavHostController,onRegisterClick: (event: Event) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Eventpage(onRegisterClick =onRegisterClick)
    }
}