package com.example.forecaster.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.forecaster.data.model.Weather

class WeatherDiffCallback : ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }
}