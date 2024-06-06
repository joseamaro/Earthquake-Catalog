package com.pro.earthquakecatalog.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature

@Composable
fun MapEarthquake(
    modifier: Modifier = Modifier,
    earthquakePagingItems: LazyPagingItems<EarthquakeFeature>,
    cameraPosition: CameraPositionState
) {
    val routeCoordinates = earthquakePagingItems.itemSnapshotList.items
    val markers =
        routeCoordinates.map { LatLng(it.geometry.coordinates[1], it.geometry.coordinates[0]) }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
        onMapLoaded = {
            if (routeCoordinates.isNotEmpty()) {
                val boundsBuilder = LatLngBounds.Builder()
                for (marker in markers) {
                    boundsBuilder.include(marker)
                }
                val bounds = boundsBuilder.build()
                cameraPosition.move(update = CameraUpdateFactory.newLatLngBounds(bounds, 10))
            }
        }
    ) {
        if (routeCoordinates.isNotEmpty()) {
            routeCoordinates.forEach { earthquake ->
                val positionMarker =
                    LatLng(earthquake.geometry.coordinates[1], earthquake.geometry.coordinates[0])
                MarkerInfoWindow(
                    state = MarkerState(position = positionMarker)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(Color.Gray)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = earthquake.properties.title,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Place: ")
                            Text(
                                text = earthquake.properties.place,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Depth: ")
                            Text(
                                text = earthquake.geometry.coordinates[2].toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Magnitude: ")
                            Text(
                                text = earthquake.properties.mag.toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                    }
                }
            }
        }
    }
}