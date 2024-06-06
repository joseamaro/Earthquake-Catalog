package com.pro.earthquakecatalog.authentication.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pro.earthquakecatalog.authentication.domain.usecase.AuthenticationUseCase
import com.pro.earthquakecatalog.authentication.domain.usecase.ValidateEmailUseCase
import com.pro.earthquakecatalog.authentication.domain.usecase.ValidatePasswordUseCase
import com.pro.earthquakecatalog.authentication.login.LoginEvent
import com.pro.earthquakecatalog.authentication.login.LoginState
import com.pro.earthquakecatalog.authentication.util.EmailErrorParser
import com.pro.earthquakecatalog.authentication.util.PasswordErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun loginEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EventEmail -> {
                _uiState.update { state ->
                    state.copy(
                        email = event.email
                    )
                }
            }

            is LoginEvent.EventPassword -> {
                _uiState.update { state ->
                    state.copy(
                        password = event.password
                    )
                }
            }

            LoginEvent.EventLogin -> {
                login(uiState.value.email, uiState.value.password)
            }

            LoginEvent.EventRegister -> {
                _uiState.update { state ->
                    state.copy(
                        signUp = true,
                    )
                }
            }
        }
    }

    fun login(email: String, password: String) {
        val passwordResult = validatePasswordUseCase(password)
        val isEmailValid = validateEmailUseCase(email)
        _uiState.update { state ->
            state.copy(
                emailError = EmailErrorParser.errorEmail(isEmailValid),
                passwordError = PasswordErrorParser.parserError(passwordResult)
            )
        }
        if (uiState.value.emailError == null && uiState.value.passwordError == null) {
            viewModelScope.launch {
                _uiState.update { state ->
                    state.copy(loading = true)
                }
                authenticationUseCase(email = email, password = password).onSuccess {
                    _uiState.update { state ->
                        state.copy(loggedIn = true)
                    }
                }.onFailure {
                    println("${it.message}")
                }
                _uiState.update { state ->
                    state.copy(loading = false)
                }
            }
        }
    }
}