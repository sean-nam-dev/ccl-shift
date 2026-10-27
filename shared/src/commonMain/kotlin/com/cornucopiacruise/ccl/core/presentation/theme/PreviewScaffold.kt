package com.cornucopiacruise.ccl.core.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PreviewScaffold(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.background(Color.White)
            .padding(20.dp)
    ) {
        content()
    }
}