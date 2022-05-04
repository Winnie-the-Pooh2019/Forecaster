package com.example.forecaster.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.R
import com.example.forecaster.model.ListItem

class WeatherAdapter : ListAdapter<ListItem, RecyclerView.ViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            HotHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_hot, parent, false))
        else
            ColdHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_cold, parent, false))
    }

    override fun getItemViewType(position: Int): Int = if (currentList[position].main.temp >= 0) 0 else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WeatherHolder).bind(currentList[position])
    }

    class HotHolder(view: View) : WeatherHolder(view)

    class ColdHolder(view: View) : WeatherHolder(view)

    open class WeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateTime: TextView = view.findViewById(R.id.datetime_text)
        private val temperature: TextView = view.findViewById(R.id.temperature_text)

        fun bind(listItem: ListItem) {
            temperature.text = listItem.main.temp.toString()
            dateTime.text = listItem.dt_txt
        }
    }
}