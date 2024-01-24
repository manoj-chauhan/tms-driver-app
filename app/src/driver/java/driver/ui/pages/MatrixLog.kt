package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import driver.ui.viewmodels.MatrixLogViewModel
import java.text.SimpleDateFormat

@Composable
fun MatrixLog(vm: MatrixLogViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val matList by vm.matrixList.collectAsStateWithLifecycle()
    vm.loadMatrixLog(context = context)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        if(matList?.size != 0 ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Button(onClick = { vm.deleteMatrix(context) }) {
                    Text(text = "Clear Database")
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 60.dp)) {
            matList?.let {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(
                        items = matList!!.reversed(), // Reverse the order of the list
                        itemContent = { _, mat ->
                            MatrixRecord(mat)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MatrixRecord(mat: com.samrish.driver.database.Telemetry) {
    val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss")
    val outputFormat = SimpleDateFormat("dd-MMM HH:mm:ss")

    val parsedDate = remember(mat.time) { inputFormat.parse(mat.time.toString()) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }
    Row {
        Text(text = formattedDate,style = TextStyle(color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(text = "${mat.latitude.format(2)},${mat.longitude.format(2)}, ${mat.isDataLoaded}")
    }
    Spacer(modifier = Modifier.height(10.dp))
}

    fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Preview
@Composable
fun UserDataFetchPreview() {
    MatrixLog()
}