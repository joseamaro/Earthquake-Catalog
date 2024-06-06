package com.pro.earthquakecatalog.detail.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailEarthquakeScreen(detailViewModel: DetailViewModel = hiltViewModel(), onBack: () -> Unit) {

    val state = detailViewModel.uiState.collectAsState().value

    val positionMarker =
        LatLng(
            state.detail?.geometry?.coordinates?.get(1) ?: 0.0,
            state.detail?.geometry?.coordinates?.get(0) ?: 0.0
        )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(positionMarker, 50f)
    }

    Scaffold(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth(), topBar = {
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = {
                onBack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Detail",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState
            ) {
                MarkerInfoWindow(
                    state = MarkerState(position = positionMarker),
                    title = "marker earthquake"
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
                            text = state.detail?.properties?.title.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Place: ")
                            Text(
                                text = state.detail?.properties?.place.toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Depth: ")
                            Text(
                                text = state.detail?.geometry?.coordinates?.get(2).toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Magnitude: ")
                            Text(
                                text = state.detail?.properties?.mag.toString(),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                    }
                }
            }
        }
    }
    LaunchedEffect(positionMarker) {
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(positionMarker, 15f))
    }
}