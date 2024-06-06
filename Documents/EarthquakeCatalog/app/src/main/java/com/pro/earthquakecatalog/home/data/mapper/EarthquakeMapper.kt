package com.pro.earthquakecatalog.home.data.mapper

import com.pro.earthquakecatalog.home.data.remote.response.EarthquakeFeatureEntity
import com.pro.earthquakecatalog.home.data.remote.response.EarthquakeResponse
import com.pro.earthquakecatalog.home.data.remote.response.GeometryEntity
import com.pro.earthquakecatalog.home.data.remote.response.PropertiesEntity
import com.pro.earthquakecatalog.home.domain.model.Earthquake
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import com.pro.earthquakecatalog.home.domain.model.Geometry
import com.pro.earthquakecatalog.home.domain.model.Properties

fun EarthquakeResponse.toDomain() : Earthquake {
    return Earthquake(
        features = features.map {
            it.toDomain()
        }
    )
}

fun EarthquakeFeatureEntity.toDomain() : EarthquakeFeature {
    return EarthquakeFeature(
        type = type,
        properties = properties.toDomain(),
        geometry = geometry.toDomain(),
        id = id
    )
}

fun GeometryEntity.toDomain() : Geometry {
    return Geometry(
        type = type,
        coordinates = coordinates
    )
}

fun PropertiesEntity.toDomain() : Properties {
    return Properties(
        mag = mag,
        place = place,
        time = time,
        updated = updated,
        status = status,
        tsunami = tsunami,
        ids = ids,
        type = type,
        title = title
    )
}