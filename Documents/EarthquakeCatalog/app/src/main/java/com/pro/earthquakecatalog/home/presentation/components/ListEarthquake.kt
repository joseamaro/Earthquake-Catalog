package com.pro.earthquakecatalog.home.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import com.pro.earthquakecatalog.home.presentation.LoadingEarthquake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ListEarthquakes(
    modifier: Modifier = Modifier,
    earthquakePagingItems: LazyPagingItems<EarthquakeFeature>,
    coroutineScope: CoroutineScope,
    state: LazyListState,
    onClickItem: (String) -> Unit
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.background(Color.Gray.copy(
            alpha = 0.2F
        )),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = state
    ) {
        items(earthquakePagingItems.itemCount) { index ->
            EarthquakeItem(earthquakePagingItems[index]!!) {
                onClickItem(it)
            }
        }
        earthquakePagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    coroutineScope.launch {
                        state.animateScrollToItem(0)
                    }

                }

                loadState.refresh is LoadState.Error -> {
                    val error = earthquakePagingItems.loadState.refresh as LoadState.Error
                    item {
                        Toast.makeText(
                            context,
                            error.error.localizedMessage!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingEarthquake(Modifier.fillMaxWidth())
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = earthquakePagingItems.loadState.append as LoadState.Error
                    item {
                        Toast.makeText(
                            context,
                            error.error.localizedMessage!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun EarthquakeItem(earthquake: EarthquakeFeature, onClickItem: (String) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth().clickable {
                onClickItem(earthquake.id)
            },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = earthquake.properties.title,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "Magnitude: ${earthquake.properties.mag}")
                Text(text = "Place: ${earthquake.properties.place}")
            }

            Text(
                text = "Dee detail",
                modifier = Modifier.align(Alignment.End),
                color = Color.Red
            )
        }
    }
}
