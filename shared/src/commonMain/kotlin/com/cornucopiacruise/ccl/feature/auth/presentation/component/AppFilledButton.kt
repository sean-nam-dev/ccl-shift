package com.cornucopiacruise.ccl.feature.auth.presentation.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.register
import com.cornucopiacruise.ccl.feature.util.PreviewScaffold
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    contentText: String,
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick,
        shape = ButtonDefaults.squareShape,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        if (content == null) {
            Text(
                text = contentText
            )
        } else {
            content()
        }
    }
}

@Preview
@Composable
private fun AppFilledButtonPreview() {
    PreviewScaffold {
        AppFilledButton(
            contentText = stringResource(Res.string.register),
            onClick = {}
        )
    }
}