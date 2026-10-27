package com.cornucopiacruise.ccl.feature.auth.presentation.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cornucopiacruise.ccl.core.presentation.theme.PreviewScaffold

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    contentText: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = ButtonDefaults.squareShape
    ) {
        Text(
            text = contentText
        )
    }
}

@Preview
@Composable
private fun AppOutlinedButtonPreview() {
    PreviewScaffold {
        AppOutlinedButton(
            contentText = "Log In",
            onClick = {}
        )
    }
}