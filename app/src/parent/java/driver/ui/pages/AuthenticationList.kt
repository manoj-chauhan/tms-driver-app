package driver.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent

data class UserData(
    val name:String,
    val token:String
)
@Composable
fun UserList(onUserSelected: (selected:String) -> Unit) {

    val userList= listOf(
        UserData("anirudh","671fb7721f0ca45ae537338bf"),
        UserData("atul","661fb7721f0ca45ae537338bf"),
        UserData("ankit","701fb7721f0ca45ae537338bf"),
        UserData("jitesh","681fb7721f0ca45ae537338bf"),
        UserData("dhruvjoshi","691fb7721f0ca45ae537338bf"),
//        UserData("manoj",""),


    )

    Column {
        userList.forEach { user ->
            UserPreview(user,onUserSelected={onUserSelected(user.token)})

        }
    }
}

@Composable
fun UserPreview(user: UserData, onUserSelected: (selected: String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onUserSelected(user.token) }
            .padding(13.dp, top = 10.dp, end = 13.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray,

            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),

            shape = RoundedCornerShape(2.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()){
                Text(text=user.name)
            }


        }
    }

}



