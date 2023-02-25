package com.coforge.amadeus.common.footer

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.coforge.amadeus.databinding.ItemPagingFooterBinding
import com.coforge.amadeus.utils.executeWithAction

/**
 * Created by Suman Singh on 7/7/2022.
 */
class FooterViewHolder(
    private val binding: ItemPagingFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.executeWithAction {
            footerUiState = FooterUiState(loadState)
        }
    }
}
