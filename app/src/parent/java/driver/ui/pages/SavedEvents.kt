package driver.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

enum class HomeTabs(

    val text: String,
) {
    All(
        text = "All"
    ),
    Saved(
        text = "Saved"

    )


}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SavedEvents() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })


    Scaffold(
        topBar = { Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth().height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(modifier = Modifier.width(60.dp).align(Alignment.CenterVertically)) {
                IconButton(modifier = Modifier.size(25.dp).align(Alignment.Center), onClick = {

                }) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Events", style =  TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }


        } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        modifier = Modifier.height(40.dp),
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            selectedTabIndex=index
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)

                            }
                        },
                        text = { Text(text = currentTab.text) },
                    )
                }
            }
            when(selectedTabIndex){
                0-> {
                    Eventpage()
                }
                1->{
                    Box(modifier = Modifier.fillMaxSize())

                }
            }
        }
    }




}





