package com.coforge.amadeus.common.footer

import android.content.Context
import androidx.paging.LoadState
import com.coforge.amadeus.R
import com.coforge.amadeus.common.base.BaseUiState


data class FooterUiState(private val loadState: LoadState) : BaseUiState() {

    fun getLoadingVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)

    fun getErrorVisibility() = getViewVisibility(isVisible = loadState is LoadState.Error)

    fun getErrorMessage(context: Context) = if (loadState is LoadState.Error) {
        loadState.error.localizedMessage ?: context.getString(R.string.something_went_wrong)
    } else ""
}