package com.pro.earthquakecatalog.authentication.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pro.earthquakecatalog.authentication.domain.usecase.RegisterUseCase
import com.pro.earthquakecatalog.authentication.domain.usecase.ValidateEmailUseCase
import com.pro.earthquakecatalog.authentication.domain.usecase.ValidatePasswordUseCase
import com.pro.earthquakecatalog.authentication.register.RegisterEvent
import com.pro.earthquakecatalog.authentication.register.RegisterState
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
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Name -> {
                _uiState.update { state ->
                    state.copy(name = event.name)
                }
            }

            is RegisterEvent.LastName -> {
                _uiState.update { state ->
                    state.copy(lastName = event.lastName)
                }
            }

            is RegisterEvent.Email -> {
                _uiState.update { state ->
                    state.copy(email = event.email)
                }
            }

            is RegisterEvent.Password -> {
                _uiState.update { state ->
                    state.copy(password = event.password)
                }
            }

            RegisterEvent.EventLogin -> {
                _uiState.update { state ->
                    state.copy(logIn = true)
                }
            }

            RegisterEvent.EventRegisterAccount -> {
                registerUser(
                    email = uiState.value.email,
                    password = uiState.value.password,
                    name = uiState.value.name,
                    lastName = uiState.value.lastName
                )
            }
        }
    }

    private fun registerUser(email: String, password: String, name: String, lastName: String) {
        val emailValid = validateEmailUseCase(email)
        val passwordResult = validatePasswordUseCase(password)
        val nameResult = validateName(name)
        val lastNameResult = validateLastName(lastName)
        _uiState.update { state ->
            state.copy(
                emailError = EmailErrorParser.errorEmail(emailValid),
                passwordError = PasswordErrorParser.parserError(passwordResult),
                nameError = nameResult,
                lastNameError = lastNameResult
            )
        }
        if (uiState.value.emailError == null && uiState.value.passwordError == null &&
            uiState.value.nameError == null && uiState.value.lastNameError == null
        ) {
            viewModelScope.launch {
                _uiState.update { state ->
                    state.copy(isLoading = true)
                }
                val result = registerUseCase(email = email, password = password)
                result.onSuccess {
                    _uiState.update { state ->
                        state.copy(goToHome = true)
                    }
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(emailError = it.message)
                    }
                }
                _uiState.update { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    private fun validateName(name: String): String? {
        return if (name.isNotEmpty())
            null
        else INVALID_NAME_EMPTY
    }

    private fun validateLastName(lastName: String): String? {
        return if (lastName.isNotEmpty())
            null
        else INVALID_LAST_NAME_EMPTY
    }

    companion object {
        private const val INVALID_NAME_EMPTY = "The name cannot be empty"
        private const val INVALID_LAST_NAME_EMPTY = "The last name cannot be empty"
    }
}