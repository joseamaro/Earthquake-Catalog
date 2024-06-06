package com.pro.earthquakecatalog.home.domain.model

data class EarthquakeFeature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry,
    val id: String
)