package com.drishto.driver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun GeneratedCodeDialog( assignmentCode: String,
    setShowDialog: () -> Unit
) {

    Dialog(onDismissRequest = { setShowDialog() }) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(text = "The Assignment Code is ")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "$assignmentCode", fontSize = 22.sp)
                }



                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp), contentAlignment = Alignment.BottomEnd) {
                    Button(
                        modifier = Modifier.background(Color.Transparent),
                        onClick = {
                            setShowDialog()
                        },
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(text = "Done")
                    }
                }
            }

        }
    }
}
