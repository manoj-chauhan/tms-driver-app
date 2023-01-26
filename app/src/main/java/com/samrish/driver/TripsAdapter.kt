package com.samrish.driver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class TripsAdapter() : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    private var tripList: ArrayList<Trip> = ArrayList()

    init {
        tripList.add(Trip("A","1"));
        tripList.add(Trip("B","1"));
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
        val flower = tripList[position]
        holder.bind(flower)
    }

    override fun getItemCount(): Int {
        return tripList.size
    }
}
