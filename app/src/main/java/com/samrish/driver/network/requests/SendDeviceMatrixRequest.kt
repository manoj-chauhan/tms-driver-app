package com.samrish.driver.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import org.json.JSONObject
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SendDeviceMatrixRequest(deviceIdentifier: String,
                              latitude: Double,
                              longitude: Double,
                              url: String,
                              headers: MutableMap<String, String>,
                              listener: Response.Listener<String>,
                              errorListener: Response.ErrorListener
) : GenericRequest<String>(Method.POST, url, headers, listener, errorListener) {

    private var deviceIdentifier: String
    private var latitude: Double
    private var longitude: Double

    init {
        this.deviceIdentifier = deviceIdentifier
        this.latitude = latitude
        this.longitude = longitude
    }

    override fun transformResponse(response: NetworkResponse?): String {
        return String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
    }

    override fun getBodyContentType(): String {
        return "application/json; charset=$paramsEncoding"
    }

    override fun getBody(): ByteArray? {
        var body: JSONObject = JSONObject()
        body.put("deviceIdentifier", deviceIdentifier)
        body.put("latitude", this.latitude)
        body.put("longitude", this.longitude)
        body.put("time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()))
        return body.toString().encodeToByteArray()
    }
}