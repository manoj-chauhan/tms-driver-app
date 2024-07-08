package com.drishto.driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drishto.driver.R


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginScreen() {

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val fontName = GoogleFont("Russo One")

    val fontFamily = FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )
    var text by remember { mutableStateOf(TextFieldValue("")) }

    val textStyle = TextStyle(
        fontFamily = FontFamily(
            Font(googleFont = fontName, fontProvider = provider)
        ),
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0XFFFDF2F2))
            .padding(top = 60.dp)
            .padding(horizontal = 10.dp),


        horizontalAlignment = Alignment.CenterHorizontally
    ) {




        Box(
            modifier = Modifier.fillMaxWidth(),

            contentAlignment = Alignment.TopCenter
        ) {


            Image(
                painter = painterResource(id = R.drawable.dd),
                contentDescription = "dd",
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))


//        Text(
//            text = "Drishto Drive",
//            fontFamily = fontFamily,
//            fontSize = 26.sp,
//
//            fontWeight = FontWeight.ExtraBold,
//
//            color = Color(0XFF000000),
//
//            textAlign = TextAlign.Center
//        )

        BasicText(
            text = "Drishto Drive",
            style = textStyle
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "An Effort to make travel safer....", color = Color.Black, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(110.dp))

        Text(text = "Create Account or Sign In", color = Color(0XFF3D0000), fontSize = 18.sp
        , fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(40.dp))

        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = "Enter your phone number",
                    color = Color.Gray,
                    fontFamily = fontFamily
                )
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 20.dp
            ),
            onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE94234)
            )
        ) {
            Text(
                text = "Send OTP",
                modifier = Modifier,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

//

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){

            HorizontalDivider(thickness = 2.dp, color = Color.Black,modifier= Modifier
                .weight(1f)
                .padding(horizontal = 8.dp))
            Text(text = "or", fontWeight = FontWeight.Bold)
            HorizontalDivider(thickness = 2.dp, color = Color.Black,modifier= Modifier
                .weight(1f)
                .padding(horizontal = 8.dp))


        }
        Spacer(modifier = Modifier.height(30.dp))




        Button(modifier= Modifier
            .height(45.dp)
            .width(700.dp)
            .padding(horizontal = 5.dp)
            .border(
                width = 2.dp,
                color = Color(0XFF800000),
                shape = RoundedCornerShape(12.dp)
            ),
//            shape = RoundedCornerShape(12.dp),
            onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
                )

            ) {

            Row(
                modifier = Modifier
                    .background(Color.Transparent)

                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(60.dp),
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "google"
                )

//                Spacer(modifier = Modifier.width(2.dp))

                Text(text = "Continue with Google", fontSize = 16.sp, color = Color.Black, )
            }


        }
        Spacer(modifier = Modifier.height(70.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center) {


            TermsAndPrivacyText(
                onTermsClick = { },
                onPrivacyClick = { }
            )
        }

    }

}

@Composable
fun TermsAndPrivacyText(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append("By continuing you agree that you have read and accepted our ")
        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Terms Of Use")
        }
        pop()
        append(" and ")
        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy")
        }
        pop()
        append(".")
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center

        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                .firstOrNull()?.let {
                    onTermsClick()
                }
            annotatedString.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                .firstOrNull()?.let {
                    onPrivacyClick()
                }
        }
    )
}

fun DrawScope.drawTopLeftShape() {
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(0f, 0f)
        lineTo(0f, 200.dp.toPx())
        lineTo(200.dp.toPx(), 0f)
        close()
    }
    drawPath(path, color = Color.Red)
}



//fun DrawScope.drawBottomArcShape() {
//    val arcRect = Rect(
//        left = -size.width / 2,
//        top = size.height / 1.5f,
//        right = size.width + size.width / 2,
//        bottom = size.height + size.height / 2
//    )
//    drawArc(
//        color = Color(0xFFFFCDD2),
//        startAngle = 180f,
//        sweepAngle = 180f,
//        useCenter = true,
//        topLeft = arcRect.topLeft,
//        size = arcRect.size.toSize()
//    )
//}


