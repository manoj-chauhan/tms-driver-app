package driver

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.drishto.driver.R
import dagger.hilt.android.AndroidEntryPoint
import driver.ui.DrishtoParentApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("TAG", "onStart: ")
        super.onCreate(savedInstanceState)
        var batteryOptimizationDialogShown by mutableStateOf(false)

        setTitle(R.string.app_name)

        setContent {
            DrishtoParentApp()
        }
    }

}