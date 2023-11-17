package com.samrish.driver.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.models.Documents
import com.samrish.driver.ui.viewmodels.DocumentDownloadViewModel

@Composable
fun DocumentsDialog(operatorId:Int,document: MutableList<Documents>) {
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Documents", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                    if(document.size == 0 ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "No Documents Found!!")
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    document.forEach { document -> DocumentsList(operatorId,context,document = document) }
                }

            }
}

@Composable
fun DocumentsList(operatorId: Int,context: Context, document: Documents, vm: DocumentDownloadViewModel = viewModel(),){
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
        Column(modifier = Modifier.width(200.dp)) {
            Box(modifier = Modifier.fillMaxWidth()){
                Text(text = document.type, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold))
            }
            Box(modifier = Modifier.fillMaxWidth()){
                Text(text = document.name, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light, color = Color.Gray))
            }
        }

        Box(modifier = Modifier.width(30.dp)){
            Icon(imageVector = Icons.Filled.Download, contentDescription = null, Modifier.clickable { vm.downloadDocument(context = context, name = document.name, operatorId = operatorId) })
        }
    }

    Spacer(modifier = Modifier.padding(8.dp))

}


