package com.cornucopiacruise.ccl.feature.auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cornucopiacruise.ccl.core.presentation.theme.PreviewScaffold
import com.cornucopiacruise.ccl.core.presentation.theme.dimens

@Composable
fun TextedDivider(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )

        Text(
            text = text
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
private fun TextedDividerPreview() {
    PreviewScaffold {
        TextedDivider(
            modifier = Modifier.fillMaxWidth(),
            text = "Or"
        )
    }
}