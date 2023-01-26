package com.samrish.driver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TripsAdapter(trips:List<Trip>) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    private var tripList: List<Trip> = ArrayList()

    init {
        this.tripList = trips
    }

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tripNameView: TextView = itemView.findViewById(R.id.trip_card_name)
        private val tripCodeView: TextView = itemView.findViewById(R.id.trip_card_code)

        fun bind(trip: Trip) {
            tripNameView.text = trip.name
            tripCodeView.text = trip.code
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.trip_card, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val flower = this.tripList[position]
        holder.bind(flower)
    }

    override fun getItemCount(): Int {
        return tripList.size
    }
}
