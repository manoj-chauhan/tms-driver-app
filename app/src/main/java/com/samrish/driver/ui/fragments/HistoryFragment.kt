package com.samrish.driver.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.material3.Text;
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                TripList(
                    tripList = listOf(
                        "BH4-BH5-BH6",
                        "BH4-BH5-BH6",
                        "BH4-BH5-BH6",
                        "BH4-BH5-BH6"
                    )
                )
            }
        }
    }
}


@Composable
fun TripDetail(trip: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "3465464"
                )
                Text(
                    text = "25 Jun 23 03:00",
                    color = Color.Gray
                )
                Text(
                    text = "STARTED",
                    color = Color.Green
                )
            }
            Row() {
                Text(
                    text = trip
                )

            }

        }
    }
}


@Composable
fun TripList(tripList: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        tripList.forEach { trip -> TripDetail(trip) }
    }
}

@Preview
@Composable
fun TripListPreview() {
    TripList(tripList = listOf("BH4-BH5-BH6", "BH4-BH5-BH6", "BH4-BH5-BH6", "BH4-BH5-BH6"))
}