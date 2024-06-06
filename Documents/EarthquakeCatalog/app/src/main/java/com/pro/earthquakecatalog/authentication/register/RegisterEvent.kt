package com.pro.earthquakecatalog.authentication.register

sealed interface RegisterEvent {
    data class Email(val email: String) : RegisterEvent
    data class Password(val password: String) : RegisterEvent
    data class Name(val name: String) : RegisterEvent
    data class LastName(val lastName: String) : RegisterEvent
    data object EventRegisterAccount : RegisterEvent
    data object EventLogin : RegisterEvent
}