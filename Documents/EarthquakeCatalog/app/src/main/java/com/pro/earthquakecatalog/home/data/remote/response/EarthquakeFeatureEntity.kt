package com.pro.earthquakecatalog.home.data.remote.response

data class EarthquakeFeatureEntity(
    val type: String,
    val properties: PropertiesEntity,
    val geometry: GeometryEntity,
    val id: String
)
