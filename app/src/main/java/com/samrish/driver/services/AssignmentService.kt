import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.services.requests.TripListRequest

fun getTrips(context: Context, onTripsFetched:(trips: List<Trip>)->Unit) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trips_list)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = context.let { SessionStorage().getAccessToken(it) }
    if (authHeader != null) {
        hdrs["Authorization"] = "Bearer $authHeader"
    }

    val stringRequest = TripListRequest(url, hdrs, { response ->
        onTripsFetched(response)
    },
        { error ->
            run {
                Log.i("TripList", "Request Failed with Error: $error")
                if (error is TimeoutError || error is NoConnectionError) {
                    Toast.makeText(context, "Couldn't Connect!", Toast.LENGTH_LONG).show();
                } else if (error is AuthFailureError) {
//                goToLogin();
                } else if (error is ServerError) {
                    Toast.makeText(context, "Server error!", Toast.LENGTH_LONG).show();
                } else if (error is NetworkError) {
                    Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show();
                } else if (error is ParseError) {
                    Toast.makeText(context, "Unable to parse response", Toast.LENGTH_LONG)
                        .show();
                }
            }
        })
    queue.add(stringRequest)
}