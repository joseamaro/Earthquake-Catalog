package com.pro.earthquakecatalog.home.presentation

sealed interface HomeEvent {
    data class EventUpdate(val checked: Boolean) : HomeEvent
    data class EventFilter(val showFilterDate: Boolean): HomeEvent
}