package com.coforge.amadeus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coforge.amadeus.databinding.ItemCityLayoutBinding
import com.coforge.amadeus.models.WeatherItemUiState
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

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    companion object {
        private const val VIEW_CLOUD = 0
        private const val VIEW_RAIN = 1
        private const val VIEW_MIST = 2
        private const val VIEW_FOG = 3


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




















