package com.taxi.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(
    private var cars: List<Car>,
    private val onBookClick: (Car) -> Unit,
    private val onDetailsClick: (Car) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    fun updateList(newList: List<Car>) {
        cars = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bind(car)
    }

    override fun getItemCount(): Int = cars.size

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCarModel: TextView = itemView.findViewById(R.id.tvCarModel)
        private val tvCarBrand: TextView = itemView.findViewById(R.id.tvCarBrand)
        private val tvCarPrice: TextView = itemView.findViewById(R.id.tvCarPrice)
        private val tvCarTransmission: TextView = itemView.findViewById(R.id.tvCarTransmission)
        private val tvCarFuel: TextView = itemView.findViewById(R.id.tvCarFuel)
        private val ivCarImage: ImageView = itemView.findViewById(R.id.ivCarImage)
        private val btnBook: Button = itemView.findViewById(R.id.btnBook)
        private val btnDetails: Button = itemView.findViewById(R.id.btnDetails)

        fun bind(car: Car) {
            tvCarModel.text = car.model
            tvCarBrand.text = car.brand
            tvCarPrice.text = "${car.pricePerDay}Р в день"
            tvCarTransmission.text = car.transmission
            tvCarFuel.text = car.fuel
            ivCarImage.setImageResource(car.imageRes)

            btnBook.setOnClickListener { onBookClick(car) }
            btnDetails.setOnClickListener { onDetailsClick(car) }
        }
    }
}
