package com.samrish.driver.ui.components

import android.content.Context
import android.graphics.Paint.Style
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.generatedCode
import com.samrish.driver.services.getAccessToken
import com.samrish.driver.services.handleError
import com.samrish.driver.services.requests.CodeGeneratorRequest

// Reference: https://medium.com/@manojbhadane/android-custom-dialog-using-jetpack-compose-954d83e55af7

@Composable
fun GeneratedCodeDialog(
    setShowDialog: (Boolean) -> Unit
) {

    val tripDetail = remember {
        mutableStateOf<generatedCode>(generatedCode(""))
    }

    val context = LocalContext.current

    generateCode(context, onGenerateCodeFetch={
        tripDetail.value = generatedCode(it.assignmentCode);
    })
    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(text = "The Assignment Code is ")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "${tripDetail.value.assignmentCode}", fontSize = 22.sp)
                }



                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp), contentAlignment = Alignment.BottomEnd) {
                    Button(
                        modifier = Modifier.background(Color.Transparent),
                        onClick = {
//                            generateCode(context, onGenerateCodeFetch={
//                                tripDetail.value = generatedCode(it.assignmentCode);
//                            })
                            setShowDialog(false)
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
fun generateCode(context: Context,onGenerateCodeFetch: (code: generatedCode) -> Unit){
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_driver_code)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    context.applicationContext?.let {
        val authHeader = getAccessToken(it)

        getAccessToken(it)?.let {
            hdrs["Authorization"] = "Bearer $it"
        }

        val stringRequest =
            CodeGeneratorRequest( url, hdrs, { response ->
                Log.i("Driver Code", "Generated Code: ${response.assignmentCode}")
                onGenerateCodeFetch(response)
//                GeneratedCodeDialog(response)
            }, { error -> handleError(context, error) })
        queue.add(stringRequest)

    }
}


@Preview
@Composable
fun GeneratedCodeDialogPreview(){

    GeneratedCodeDialog(
        setShowDialog = {
            Log.i("Dialog", "Dialog dismissed")
        })

}
