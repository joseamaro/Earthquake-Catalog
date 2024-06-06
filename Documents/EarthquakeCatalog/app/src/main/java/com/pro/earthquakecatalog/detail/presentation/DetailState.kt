package com.pro.earthquakecatalog.detail.presentation

import com.pro.earthquakecatalog.detail.domain.model.Detail

data class DetailState(
    val detail: Detail? = null,
    val error: String = ""
)
