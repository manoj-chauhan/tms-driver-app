package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.database.User
import com.samrish.driver.ui.components.Assignment
import com.samrish.driver.viewmodels.UserInfoViewModel
import kotlin.math.log

@Composable
fun UserData(vm: UserInfoViewModel = viewModel()){

//    var userList = List<User>



    val context = LocalContext.current
    val assignment by vm.currentAssignment.collectAsStateWithLifecycle()
   vm.userInfo(context = context)


    Log.d("Function", "UserData: $assignment")

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)){
        assignment?.let{
//            Text(text = "${it.uid}")
            Log.d("TAG", "BOx: $it")
       Column(modifier = Modifier.fillMaxWidth()) {
//           userList

           assignment!!.forEach{ trip -> UserDataInfo(trip) }
        }
        }
    }
}



@Composable
fun UserDataInfo(user: User){
    Text(text = "${user}")
}

@Preview
@Composable
fun UserDataFetchPreview(){
    UserData()
}