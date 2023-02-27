package com.coforge.amadeus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coforge.amadeus.databinding.ItemCityLayoutBinding
import com.coforge.amadeus.db.entites.WeatherItemUiState
import com.coforge.amadeus.utils.executeWithAction
import javax.inject.Inject

class WeatherPagingAdapter @Inject constructor() :
    PagingDataAdapter<WeatherItemUiState, WeatherPagingAdapter.WeatherViewHolder>(COMPARATOR) {

    class WeatherViewHolder(private val binding: ItemCityLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherItemUiState: WeatherItemUiState) {
            binding.apply {
                binding.executeWithAction {
                    this.weatherItemUiState = weatherItemUiState
                }
            }
        }

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        getItem(position)?.let { weatherItemUIState -> holder.bind(weatherItemUIState) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            ItemCityLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<WeatherItemUiState>() {
            override fun areItemsTheSame(
                oldItem: WeatherItemUiState, newItem: WeatherItemUiState
            ): Boolean {
                return oldItem.getCityID() == newItem.getCityID()
            }

            override fun areContentsTheSame(
                oldItem: WeatherItemUiState, newItem: WeatherItemUiState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}




















