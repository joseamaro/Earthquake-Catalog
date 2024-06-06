package com.pro.earthquakecatalog.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import com.pro.earthquakecatalog.home.domain.usecase.GetEarthquakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEarthquakesUseCase: GetEarthquakesUseCase
) : ViewModel() {

    private val _earthquakeState: MutableStateFlow<PagingData<EarthquakeFeature>> =
        MutableStateFlow(value = PagingData.empty())
    val earthquakeState: StateFlow<PagingData<EarthquakeFeature>> = _earthquakeState.asStateFlow()

    private var _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        getEarthquakes(null)
    }

    fun homeEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EventUpdate -> {
                _uiState.update { state ->
                    state.copy(checked = !event.checked)
                }
            }
            is HomeEvent.EventFilter -> {
                _uiState.update { state ->
                    state.copy(showDialogDate = event.showFilterDate)
                }
            }
        }
    }

    fun getEarthquakes(dateRange: String?) {
        viewModelScope.launch {
            getEarthquakesUseCase(
                startTime = formatDate(dividerDate(dateRange).first).toString(),
                endTime = formatDate(dividerDate(dateRange).second).toString()
            ).distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _earthquakeState.value = pagingData
                    _uiState.update { state ->
                        state.copy(
                            startDate = formatDate(dividerDate(dateRange).first).toString(),
                            endDate = formatDate(dividerDate(dateRange).second).toString()
                        )
                    }
                }
        }
    }

    private fun dividerDate(dateRange: String?) : Pair<Long?, Long?> {
        val dates = dateRange?.split("..")?.map { it.toLong() }
        val startMillis = dates?.get(0)
        val endMillis = dates?.get(1)
        return Pair(startMillis, endMillis)
    }

    private fun formatDate(dateLong: Long?): LocalDate {
        return if (dateLong != null) {
            Instant.ofEpochMilli(dateLong).atZone(ZoneId.of("UTC")).toLocalDate()
        } else {
            LocalDate.now()
        }
    }
}