package com.samrish.driver.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import java.util.*

class TripsAdapter(trips:List<Trip>, private val onItemClick: (Trip) -> Unit) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    private var tripList: List<Trip> = ArrayList()

    init {
        this.tripList = trips
    }

    class TripViewHolder(itemView: View, val onItemClick: (Trip) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val tripNameView: TextView = itemView.findViewById(R.id.trip_card_name)
        private val tripCodeView: TextView = itemView.findViewById(R.id.trip_card_code)
        private var currentTrip: Trip? = null

        init {
            itemView.setOnClickListener {
                currentTrip?.let {
                    onItemClick(it)
                }
            }
        }

        fun bind(trip: Trip) {
            currentTrip = trip
            tripNameView.text = trip.name
            tripCodeView.text = trip.code
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.trip_card, parent, false)
        return TripViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(this.tripList[position])
    }

    override fun getItemCount(): Int {
        return tripList.size
    }
}
