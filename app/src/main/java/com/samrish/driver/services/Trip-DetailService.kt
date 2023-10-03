package com.samrish.driver.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

fun click(context: Context) {

    val url = "https://test.drishto.com/login"
    val intent = Intent()
        .setAction(Intent.ACTION_VIEW)
        .setData(Uri.parse(url))

    context.startActivity(intent)

}