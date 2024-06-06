package com.pro.earthquakecatalog.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatesEarthquake(modifier: Modifier = Modifier, starDate: String, endDate: String) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Row {
            Text(text = "Start date: ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = starDate, fontSize = 12.sp)
        }
        Row {
            Text(text = "End date: ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = endDate, fontSize = 12.sp)
        }
    }
}