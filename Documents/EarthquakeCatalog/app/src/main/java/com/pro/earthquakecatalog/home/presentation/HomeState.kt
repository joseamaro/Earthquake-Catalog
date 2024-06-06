package com.pro.earthquakecatalog.home.presentation

import androidx.paging.PagingData
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import java.time.LocalDate

data class HomeState(
    val pagingData: PagingData<EarthquakeFeature> = PagingData.empty(),
    val startDate: String = LocalDate.now().toString(),
    val endDate: String = LocalDate.now().toString(),
    val showDialogDate: Boolean = false,
    val checked: Boolean = false
)
