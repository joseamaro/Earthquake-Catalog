package com.pro.earthquakecatalog.authentication.login

sealed interface LoginEvent {
    data class EventEmail(val email: String): LoginEvent
    data class EventPassword(val password: String): LoginEvent
    data object EventLogin : LoginEvent
    data object EventRegister : LoginEvent
}