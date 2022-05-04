package com.example.forecaster.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.forecaster.model.ListItem

class WeatherDiffCallback : ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}