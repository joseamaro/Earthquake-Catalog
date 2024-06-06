package com.pro.earthquakecatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pro.earthquakecatalog.authentication.domain.usecase.GetUserIdUseCase
import com.pro.earthquakecatalog.authentication.domain.usecase.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    validateLoginUseCase: GetUserIdUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private var _isLoggedInState = MutableStateFlow(validateLoginUseCase.invoke())
    val isLoggedInState: StateFlow<Boolean> = _isLoggedInState.asStateFlow()


    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }
}