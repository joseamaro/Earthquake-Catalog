package com.pro.earthquakecatalog.detail.presentation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pro.earthquakecatalog.detail.domain.usecase.DetailEarthquakeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailEarthquakeUseCase: DetailEarthquakeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState = MutableStateFlow(DetailState())
    val uiState: StateFlow<DetailState> = _uiState.asStateFlow()


    init {
        val earthquakeId = savedStateHandle.get<String>("earthquakeId")

        if (earthquakeId != null) {
            viewModelScope.launch {
                detailEarthquakeUseCase.invoke(earthquakeId).onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            detail = it
                        )
                    }
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(
                            error = "an error occurred"
                        )
                    }
                }
            }
        }
    }
}