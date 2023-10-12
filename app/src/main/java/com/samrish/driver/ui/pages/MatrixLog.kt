package com.samrish.driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.database.Matrix
import com.samrish.driver.viewmodels.MatrixLogViewModel

@Composable
fun MatrixLog(vm: MatrixLogViewModel = viewModel()) {

    val context = LocalContext.current
    val matList by vm.matrixList.collectAsStateWithLifecycle()
    vm.loadMatrixLog(context = context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        matList?.let {
            Column(modifier = Modifier.fillMaxWidth()) {
                matList!!.forEach { record -> MatrixRecord(record) }
            }
        }
    }
}


@Composable
fun MatrixRecord(mat: Matrix) {
    Text(text = "${mat.time} ${mat.latitude},${mat.longitude}")
}

@Preview
@Composable
fun UserDataFetchPreview() {
    MatrixLog()
}