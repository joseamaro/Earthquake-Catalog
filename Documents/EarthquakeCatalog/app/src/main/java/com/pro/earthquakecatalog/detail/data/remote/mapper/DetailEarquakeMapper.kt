package com.pro.earthquakecatalog.detail.data.remote.mapper

import com.pro.earthquakecatalog.detail.data.remote.response.DetailResponse
import com.pro.earthquakecatalog.detail.data.remote.response.GeometryDetailEntity
import com.pro.earthquakecatalog.detail.data.remote.response.PropertiesDetailEntity
import com.pro.earthquakecatalog.detail.domain.model.Detail
import com.pro.earthquakecatalog.detail.domain.model.GeometryDetail
import com.pro.earthquakecatalog.detail.domain.model.PropertiesDetail

fun DetailResponse.toDomain(): Detail {
    return Detail(
        properties = properties.toDomain(),
        geometry = geometry.toDomain()
    )
}

fun PropertiesDetailEntity.toDomain(): PropertiesDetail {
    return PropertiesDetail(
        mag = mag,
        place = place,
        title = title
    )
}

fun GeometryDetailEntity.toDomain() : GeometryDetail {
    return GeometryDetail(
        coordinates = coordinates
    )
}