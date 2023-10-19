package com.samrish.driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.samrish.driver.models.Documents

@Composable
fun DocumentsDialog(document: MutableList<Documents>, setShowDialog: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Documents", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.padding(8.dp))
//                    document.forEach { document -> DocumentsList(document = document) }
                }

            }
        }


    }
}

@Composable
//fun DocumentsList( document: Documents){
fun DocumentList(){
        Box(modifier = Modifier.fillMaxWidth()){

        }
        Box(modifier = Modifier.width(30.dp)){

        }
}


@Preview
@Composable
fun DocumentListPreview(){
    DocumentList()
}
