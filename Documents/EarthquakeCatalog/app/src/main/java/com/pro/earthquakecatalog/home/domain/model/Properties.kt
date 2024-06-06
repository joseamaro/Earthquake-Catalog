package com.pro.earthquakecatalog.home.domain.model

data class Properties(
    val mag: Double,
    val place: String,
    val time: String,
    val updated: String,
    val status: String,
    val tsunami: Int,
    val ids: String,
    val type: String,
    val title: String
)
