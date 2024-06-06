package com.pro.earthquakecatalog.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.maps.android.compose.rememberCameraPositionState
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import com.pro.earthquakecatalog.home.presentation.components.DatesEarthquake
import com.pro.earthquakecatalog.home.presentation.components.DialogEarthquake
import com.pro.earthquakecatalog.home.presentation.components.ListEarthquakes
import com.pro.earthquakecatalog.home.presentation.components.MapEarthquake
import com.pro.earthquakecatalog.home.presentation.components.SwitchEarthquake

@Composable
fun HomeScreen(
    onNavigateSettings: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateDetail: (String) -> Unit
) {

    val earthquakePagingItems: LazyPagingItems<EarthquakeFeature> =
        homeViewModel.earthquakeState.collectAsLazyPagingItems()
    val state = homeViewModel.uiState.collectAsState().value
    val stateLazy = rememberLazyListState()
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(modifier = Modifier
        .background(Color.White)
        .fillMaxSize(), topBar = {
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = {
                onNavigateSettings()
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Home",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            TextButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = {
                homeViewModel.homeEvent(HomeEvent.EventFilter(true))
            }) {
                Text(text = "Filter")
            }
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SwitchEarthquake(
                    checked = state.checked,
                    onCheckedChange = { checked ->
                        homeViewModel.homeEvent(HomeEvent.EventUpdate(checked))
                    })

                DatesEarthquake(starDate = state.startDate, endDate = state.endDate)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (!state.checked) {
                    MapEarthquake(
                        modifier = Modifier.fillMaxSize(),
                        earthquakePagingItems = earthquakePagingItems,
                        cameraPosition = cameraPositionState
                    )
                } else {
                    ListEarthquakes(
                        modifier = Modifier.fillMaxSize(),
                        earthquakePagingItems = earthquakePagingItems,
                        state = stateLazy,
                        coroutineScope = coroutineScope,
                        onClickItem = {id ->
                            onNavigateDetail(id)
                        }
                    )
                }

                if (state.showDialogDate) {
                    DialogEarthquake(modifier = Modifier.fillMaxSize(), onCloset = {
                        homeViewModel.homeEvent(HomeEvent.EventFilter(false))
                    }, onDateRange = { rangeDate ->
                        homeViewModel.getEarthquakes(rangeDate.toString())
                        homeViewModel.homeEvent(HomeEvent.EventFilter(false))
                    })
                }
            }
        }
    }
}

@Composable
fun LoadingEarthquake(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}