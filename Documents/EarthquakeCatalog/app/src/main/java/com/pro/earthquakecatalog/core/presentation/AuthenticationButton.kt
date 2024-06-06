package com.pro.earthquakecatalog.core.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onFinish: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        onClick = { onFinish() }) {
        Text(text = text)
    }
}