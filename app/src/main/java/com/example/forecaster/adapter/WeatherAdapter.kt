package com.example.forecaster.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.R
import com.example.forecaster.domain.model.Weather
import timber.log.Timber

class WeatherAdapter(private val activity: AppCompatActivity) : ListAdapter<Weather, RecyclerView.ViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            HotHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_hot, parent, false))
        else
            ColdHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_cold, parent, false))
    }

    override fun getItemViewType(position: Int): Int = if (currentList[position].main.temp >= 0) 0 else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WeatherHolder).bind(currentList[position], activity.getString(R.string.city), activity)
    }

    class HotHolder(view: View) : WeatherHolder(view)

    class ColdHolder(view: View) : WeatherHolder(view)

    open class WeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateTime: TextView = view.findViewById(R.id.datetime_text)
        private val temperature: TextView = view.findViewById(R.id.temperature_text)
        private val feel: TextView = view.findViewById(R.id.text_feeling)

        fun bind(weather: Weather, city: String, activity: AppCompatActivity) {
            temperature.text = itemView.context.getString(R.string.details_temp, weather.main.temp.toString())
            dateTime.text = itemView.context.getString(R.string.details_date, weather.date)
            feel.text = itemView.context.getString(R.string.details_feel, weather.main.feelsLike.toString())

            itemView.setOnLongClickListener {
                try {
                    Intent.createChooser(Intent().apply {
                        type = "text/plain"

                        putExtra(
                            Intent.EXTRA_TEXT, """Weather in city $city:
                        |   ${temperature.text}
                        |   ${feel.text}
                        |   ${dateTime.text}
                    """.trimMargin()
                        )

                        action = Intent.ACTION_SEND
                    }, city).apply { activity.startActivity(this) }
                    true
                } catch (e: Exception) {
                    Timber.e(e)
                    false
                }
            }
        }
    }
}