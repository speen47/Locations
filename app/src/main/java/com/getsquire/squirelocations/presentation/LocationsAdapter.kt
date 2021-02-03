package com.getsquire.squirelocations.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.getsquire.squirelocations.R
import com.getsquire.squirelocations.Shop

class LocationsAdapter() : RecyclerView.Adapter<LocationViewHolder>() {

    lateinit var locations: List<Shop>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }

}

class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameOfLocation: TextView = view.findViewById(R.id.tv_name)
    private val address: TextView = view.findViewById(R.id.address)
    private val photoOfLOcation: ImageView = view.findViewById(R.id.image)

    fun bind(shop: Shop) {
        nameOfLocation.text = shop.name
        address.text = shop.address?.street
        Glide
            .with(itemView.context)
            .load(shop.photos[0].url)
            .apply(RequestOptions().placeholder(R.drawable.barbershop_placeholder).centerCrop())
            .into(photoOfLOcation)
    }
}